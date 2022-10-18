package com.example.pass.repository.booking;

import com.example.pass.repository.BaseEntity;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@ToString
@Entity
@Table(name = "booking")
public class BookingEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingSeq;    // 예약 순번

    private Long passSeq;       // 이용권 순번
    private String userId;      // 사용자 ID
    private String status;      // 상태
    private Boolean usedPass;   // 이용권 사용 여부
    private Boolean attended;   // 출석 여부
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
    private LocalDateTime cancelledAt;
}
