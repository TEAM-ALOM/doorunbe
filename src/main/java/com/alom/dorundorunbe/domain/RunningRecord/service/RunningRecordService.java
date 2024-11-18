package com.alom.dorundorunbe.domain.RunningRecord.service;

import com.alom.dorundorunbe.domain.RunningRecord.repository.RunningRecordRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RunningRecordService {
    private final RunningRecordRepository runningRecordRepository;


}
