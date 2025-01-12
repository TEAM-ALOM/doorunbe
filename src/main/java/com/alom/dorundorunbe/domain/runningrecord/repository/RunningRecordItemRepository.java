package com.alom.dorundorunbe.domain.runningrecord.repository;

import com.alom.dorundorunbe.domain.runningrecord.domain.RunningRecordItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RunningRecordItemRepository extends JpaRepository<RunningRecordItem, Long> {
}
