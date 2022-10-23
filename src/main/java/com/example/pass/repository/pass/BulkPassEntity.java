package com.example.pass.repository.pass;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "bulk_pass")
public class BulkPassEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bulkPassSeq;
    private Long packageSeq;
    private String userGroupId;

    @Enumerated(EnumType.STRING)
    private BulkPassStatus status;
    private Integer count;

    private LocalDateTime startedAt;
    private LocalDateTime endedAt;

    public static BulkPassEntity of(Long packageSeq, String userGroupId, BulkPassStatus status, Integer count, LocalDateTime startedAt, LocalDateTime endedAt) {
        return BulkPassEntity.builder()
                .packageSeq(packageSeq)
                .userGroupId(userGroupId)
                .status(status)
                .count(count)
                .startedAt(startedAt)
                .endedAt(endedAt)
                .build();
    }

    public void completed() {
        this.status = BulkPassStatus.COMPLETED;
    }
}
