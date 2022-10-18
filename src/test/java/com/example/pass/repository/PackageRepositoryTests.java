package com.example.pass.repository;

import com.example.pass.repository.packaze.PackageEntity;
import com.example.pass.repository.packaze.PackageRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
public class PackageRepositoryTests {

    @Autowired
    private PackageRepository packageRepository;

    @Test
    public void test_save() {
        PackageEntity packageEntity = PackageEntity.of("바디 챌린지 PT 12주", 1, 84);

        packageRepository.save(packageEntity);

        assertThat(packageEntity.getPackageSeq()).isNotNull();
    }

    @Test
    public void test_findByCreatedAtAfter() {
        LocalDateTime dateTime = LocalDateTime.now().minusMinutes(1);

        PackageEntity packageEntity0 = PackageEntity.of("학생 전용 3개월", 3, 90);
        PackageEntity packageEntity1 = PackageEntity.of("학생 전용 3개월", 3, 90);

        packageRepository.save(packageEntity0);
        packageRepository.save(packageEntity1);

        final List<PackageEntity> packageEntities = packageRepository.findByCreatedAtAfter(dateTime, PageRequest.of(0, 1, Sort.by("packageSeq").descending()));

        assertThat(packageEntities.size()).isEqualTo(1);
        assertThat(packageEntity1.getPackageSeq()).isEqualTo(packageEntities.get(0).getPackageSeq());
    }

    @Test
    public void test_updateCountAndPeriod() {
        PackageEntity packageEntity = PackageEntity.of("바디프로필 이벤트 4개월", 5, 90);
        packageRepository.save(packageEntity);

        int updatedCount = packageRepository.updateCountAndPeriod(packageEntity.getPackageSeq(), 30, 60);
        final PackageEntity updatedPackageEntity = packageRepository.findById(packageEntity.getPackageSeq()).get();

        assertThat(updatedCount).isEqualTo(1);
        assertThat(updatedPackageEntity.getCount()).isEqualTo(30);
        assertThat(updatedPackageEntity.getPeriod()).isEqualTo(60);
    }

    @Test
    public void test_delete() {
        PackageEntity packageEntity = PackageEntity.of("제거할 이용권", 1, 1);
        packageRepository.save(packageEntity);

        packageRepository.deleteById(packageEntity.getPackageSeq());
        boolean isEmpty = packageRepository.findById(packageEntity.getPackageSeq()).isEmpty();

        assertThat(isEmpty).isTrue();
    }
}