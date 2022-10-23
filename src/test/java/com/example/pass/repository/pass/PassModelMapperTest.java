package com.example.pass.repository.pass;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class PassModelMapperTest {
    @Test
    public void test_toPassEntity() {
        final LocalDateTime now = LocalDateTime.now();
        final String userId = "A10000000";

        BulkPassEntity bulkPassEntity = BulkPassEntity.of(1L, "GROUP", BulkPassStatus.COMPLETED, 10, now.minusDays(60), now);

        PassEntity passEntity = PassModelMapper.INSTANCE.toPassEntity(bulkPassEntity, userId);

        assertThat(passEntity.getPackageSeq()).isEqualTo(1);
        assertThat(passEntity.getStatus()).isEqualTo(PassStatus.READY);
        assertThat(passEntity.getRemainingCount()).isEqualTo(10);
        assertThat(passEntity.getStartedAt()).isEqualTo(now.minusDays(60));
        assertThat(passEntity.getEndedAt()).isEqualTo(now);
    }

}