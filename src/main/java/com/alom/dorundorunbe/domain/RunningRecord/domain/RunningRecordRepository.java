package com.alom.dorundorunbe.domain.RunningRecord.domain;

import com.alom.dorundorunbe.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RunningRecordRepository extends JpaRepository<RunningRecord, Long> {
    List<RunningRecord> findByUser(User user);
}
