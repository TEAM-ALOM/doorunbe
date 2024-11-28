package com.alom.dorundorunbe.domain.RunningRecord.repository;

import com.alom.dorundorunbe.domain.RunningRecord.domain.RunningRecord;
import com.alom.dorundorunbe.domain.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RunningRecordRepository extends JpaRepository<RunningRecord, Long> {
    Page<RunningRecord> findByUser(User user, Pageable pageable);
    List<RunningRecord> findAllByUser(User user);
    @Query("SELECT SUM(r.distance) FROM RunningRecord r WHERE r.user.id = :userId")
    Long findTotalDistanceByUserId(@Param("userId") Long userId);

    @Query("SELECT AVG(r.cadence) FROM RunningRecord r WHERE r.user.id = :userId")
    Double findAverageCadenceByUserId(@Param("userId") Long userId);

    @Query("SELECT COUNT(r) FROM RunningRecord r WHERE r.user.id = :userId AND r.date >= :startDate AND r.date <= :endDate")
    Long countRecordsByUserIdAndDateRange(@Param("userId") Long userId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

}
