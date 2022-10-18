package com.example.pass.repository.packaze;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

public interface PackageRepository extends JpaRepository<PackageEntity, Long> {
    List<PackageEntity> findByCreatedAtAfter(LocalDateTime dateTime, Pageable pageable);

    @Transactional
    @Modifying
    @Query(value =
            "UPDATE PackageEntity p " +
            "SET p.count = :count, p.period = :period " +
            "WHERE p.packageSeq = :packageSeq")
    int updateCountAndPeriod(@Param("packageSeq") Long packageSeq,
                             @Param("count") Integer count,
                             @Param("period") Integer period);
}
