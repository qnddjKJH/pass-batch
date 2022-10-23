package com.example.pass.repository.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface BookingRepository extends JpaRepository<BookingEntity, Long> {
    @Transactional
    @Modifying
    @Query(value = "UPDATE BookingEntity  b " +
            "       SET b.usedPass = :usedPass, " +
            "           b.modifiedAt = CURRENT_TIMESTAMP " +
            "       WHERE b.passSeq = :passSeq")
    int updateUsedPass(@Param("passSeq") Integer passSeq,
                       @Param("usedPass") boolean usedPass);
}
