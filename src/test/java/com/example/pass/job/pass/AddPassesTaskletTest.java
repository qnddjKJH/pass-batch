package com.example.pass.job.pass;

import com.example.pass.repository.pass.BulkPassEntity;
import com.example.pass.repository.pass.BulkPassRepository;
import com.example.pass.repository.pass.BulkPassStatus;
import com.example.pass.repository.pass.PassEntity;
import com.example.pass.repository.pass.PassRepository;
import com.example.pass.repository.pass.PassStatus;
import com.example.pass.repository.user.UserGroupMappingEntity;
import com.example.pass.repository.user.UserGroupMappingRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.repeat.RepeatStatus;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
class AddPassesTaskletTest {
    @Mock
    private StepContribution stepContribution;
    @Mock
    private ChunkContext chunkContext;
    @Mock
    private PassRepository passRepository;
    @Mock
    private BulkPassRepository bulkPassRepository;
    @Mock
    private UserGroupMappingRepository userGroupMappingRepository;

    @InjectMocks
    private AddPassesTasklet addPassesTasklet;

    @Test
    public void test_execute() throws Exception {
        final String userGroupId = "GROUP";
        final String userId = "A10000000";
        final Long packageSeq = 1L;
        final Integer count = 10;

        final LocalDateTime now = LocalDateTime.now();

        final BulkPassEntity bulkPassEntity = BulkPassEntity.of(packageSeq, userGroupId, BulkPassStatus.READY, count, now, now.plusDays(60));

        final UserGroupMappingEntity userGroupMappingEntity = UserGroupMappingEntity.of(userGroupId, userId, "GROUP", "DESCRIPTION");

        when(bulkPassRepository.findByStatusAndStartedAtGreaterThan(eq(BulkPassStatus.READY), any()))
                .thenReturn(List.of(bulkPassEntity));
        when(userGroupMappingRepository.findByUserGroupId(eq("GROUP")))
                .thenReturn(List.of(userGroupMappingEntity));

        RepeatStatus repeatStatus = addPassesTasklet.execute(stepContribution, chunkContext);

        assertThat(repeatStatus).isEqualTo(RepeatStatus.FINISHED);

        // 추가된 PassEntity 값을 확인합니다. ( 배치 작업이 끝난 후 )
        ArgumentCaptor<List> passEntitiesCaptor = ArgumentCaptor.forClass(List.class);
        verify(passRepository, times(1)).saveAll(passEntitiesCaptor.capture());
        final List<PassEntity> passEntities = passEntitiesCaptor.getValue();

        assertThat(passEntities.size()).isEqualTo(1);

        final PassEntity passEntity = passEntities.get(0);
        assertThat(passEntity.getPackageSeq()).isEqualTo(packageSeq);
        assertThat(passEntity.getUserId()).isEqualTo(userId);
        assertThat(passEntity.getStatus()).isEqualTo(PassStatus.READY);
        assertThat(passEntity.getRemainingCount()).isEqualTo(count);

    }

}