package com.alom.dorundorunbe.domain.RunningRecord.repository;

import com.alom.dorundorunbe.domain.RunningRecord.domain.RunningRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface RunningRecordRepository extends JpaRepository<RunningRecord, Long> {
    @Query("SELECT SUM(r.distance) FROM RunningRecord r WHERE r.user.id = :userId")
    Long findTotalDistanceByUserId(@Param("userId") Long userId);

    @Query("SELECT AVG(r.cadence) FROM RunningRecord r WHERE r.user.id = :userId")
    Double findAverageCadenceByUserId(@Param("userId") Long userId);

    @Query("SELECT COUNT(r) FROM RunningRecord r WHERE r.user.id = :userId AND r.date >= :startDate AND r.date <= :endDate")
    Long countRecordsByUserIdAndDateRange(@Param("userId") Long userId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
