package com.alom.dorundorunbe.domain.RunningRecord.repository;

import com.alom.dorundorunbe.domain.RunningRecord.domain.RunningRecord;
import com.alom.dorundorunbe.domain.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RunningRecordRepository extends JpaRepository<RunningRecord, Long> {
    Page<RunningRecord> findByUser(User user, Pageable pageable);
    List<RunningRecord> findAllByUser(User user);
}
