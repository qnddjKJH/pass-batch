package com.example.pass.repository.pass;

import com.example.pass.repository.BaseEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Entity
@Table(name = "pass")
public class PassEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long passSeq;

    private Long packageSeq;
    private String userId;
    private PassStatus status;
    private Long remainingCount;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
    private LocalDateTime expiredAt;

    public static PassEntity of(Long packageSeq, String userId, PassStatus status, Long remainingCount, LocalDateTime startedAt, LocalDateTime endedAt) {
        return PassEntity.builder()
                .packageSeq(packageSeq)
                .userId(userId)
                .status(status)
                .remainingCount(remainingCount)
                .startedAt(startedAt)
                .endedAt(endedAt)
                .build();
    }

    public void expirePass() {
        this.status = PassStatus.EXPIRED;
        this.expiredAt = LocalDateTime.now();
    }
}
