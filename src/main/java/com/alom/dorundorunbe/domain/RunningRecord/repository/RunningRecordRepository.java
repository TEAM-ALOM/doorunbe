package com.alom.dorundorunbe.domain.RunningRecord.repository;

import com.alom.dorundorunbe.domain.RunningRecord.domain.RunningRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RunningRecordRepository extends JpaRepository<RunningRecord, Long> {
}
