package com.alom.dorundorunbe.domain.RunningRecord.service;

import com.alom.dorundorunbe.domain.RunningRecord.domain.RunningRecord;
import com.alom.dorundorunbe.domain.RunningRecord.dto.RunningRecordEndRequestDto;
import com.alom.dorundorunbe.domain.RunningRecord.dto.RunningRecordResponseDto;
import com.alom.dorundorunbe.domain.RunningRecord.dto.RunningRecordStartRequestDto;
import com.alom.dorundorunbe.domain.RunningRecord.mapper.RunningRecordMapper;
import com.alom.dorundorunbe.domain.RunningRecord.repository.RunningRecordRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RunningRecordService {
    private final RunningRecordRepository runningRecordRepository;
    private final RunningRecordMapper runningRecordMapper;
//    private final UserRepository userRepository;

    public RunningRecordResponseDto saveStartRecord(RunningRecordStartRequestDto startRequestDto){
//        User user = userRepository.findById(startRequestDto.getUserId());
        RunningRecord runningRecord = runningRecordMapper.toEntityFromStartRequestDto(startRequestDto);
//        runningRecord.setUser(user);
        return runningRecordMapper.toResponseDto(runningRecordRepository.save(runningRecord));
    }

    public RunningRecordResponseDto saveEndRecord(RunningRecordEndRequestDto endRequestDto){
        RunningRecord runningRecord = runningRecordMapper.toEntityFromEndRequestDto(endRequestDto);
        runningRecord.setFinished(true);
        return runningRecordMapper.toResponseDto(runningRecord);
    }

    // user별 최신순 특정 개수의 기록 조회
//    public Page<RunningRecordResponseDto> findRunningRecords(Long userId, Pageable pageable){
//        User user = userRepository.findById(userId);
//        Page<RunningRecord> records = runningRecordRepository.findByUser(user, pageable);
//        return records.map(runningRecordMapper::toResponseDto);
//    }

    // 특정 기록의 상세 조회
    public RunningRecordResponseDto findRunningRecord(Long id){
        return runningRecordMapper.toResponseDto(runningRecordRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Running record with id "+id+" does not exist")));
    }
}
