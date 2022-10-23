package com.example.pass.job.pass;

import com.example.pass.repository.pass.BulkPassEntity;
import com.example.pass.repository.pass.BulkPassRepository;
import com.example.pass.repository.pass.BulkPassStatus;
import com.example.pass.repository.pass.PassEntity;
import com.example.pass.repository.pass.PassModelMapper;
import com.example.pass.repository.pass.PassRepository;
import com.example.pass.repository.user.UserGroupMappingEntity;
import com.example.pass.repository.user.UserGroupMappingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class AddPassesTasklet implements Tasklet {
    private final PassRepository passRepository;
    private final BulkPassRepository bulkPassRepository;
    private final UserGroupMappingRepository userGroupMappingRepository;

    public AddPassesTasklet(PassRepository passRepository, BulkPassRepository bulkPassRepository, UserGroupMappingRepository userGroupMappingRepository) {
        this.passRepository = passRepository;
        this.bulkPassRepository = bulkPassRepository;
        this.userGroupMappingRepository = userGroupMappingRepository;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        // 이용권 시작 일시 1일 전 user group 내 각 사용자에게 이용권을 추가해줍니다.
        final LocalDateTime startedAt = LocalDateTime.now();
        final List<BulkPassEntity> bulkPassEntities = bulkPassRepository.findByStatusAndStartedAtGreaterThan(BulkPassStatus.READY, startedAt);

        int count = 0;
        for (BulkPassEntity bulkPassEntity : bulkPassEntities) {
            final List<String> userIds = userGroupMappingRepository.findByUserGroupId(bulkPassEntity.getUserGroupId())
                    .stream().map(UserGroupMappingEntity::getUserId).collect(Collectors.toList());

            count += addPasses(bulkPassEntity, userIds);

            bulkPassEntity.completed();
        }
        // 대량 이용권 정보를 돌면서 user group 에 속한 userId 를 조회하고 해당 userId 로 이용권을 추가한다.

        log.info("AddPassesTasklet - execute: 이용권 {}건 추가 완료, startedAt={}", count, startedAt);
        return RepeatStatus.FINISHED;
    }

    // bulkPass 의 정보로 Pass 데이터 생성
    private int addPasses(BulkPassEntity bulkPassEntity, List<String> userIds) {
        List<PassEntity> passEntities = userIds.stream().map(id -> PassModelMapper.INSTANCE.toPassEntity(bulkPassEntity, id))
                .collect(Collectors.toList());

        return passRepository.saveAll(passEntities).size();
    }
}
