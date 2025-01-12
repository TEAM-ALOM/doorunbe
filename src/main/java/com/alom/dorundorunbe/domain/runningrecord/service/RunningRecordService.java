package com.alom.dorundorunbe.domain.runningrecord.service;

import com.alom.dorundorunbe.domain.item.domain.Item;
import com.alom.dorundorunbe.domain.item.dto.EquippedItemResponseDto;
import com.alom.dorundorunbe.domain.item.service.ItemService;
import com.alom.dorundorunbe.domain.runningrecord.domain.RunningRecord;
import com.alom.dorundorunbe.domain.runningrecord.domain.RunningRecordItem;
import com.alom.dorundorunbe.domain.runningrecord.dto.RunningRecordEndRequestDto;
import com.alom.dorundorunbe.domain.runningrecord.dto.RunningRecordResponseDto;
import com.alom.dorundorunbe.domain.runningrecord.dto.RunningRecordStartRequestDto;
import com.alom.dorundorunbe.domain.runningrecord.mapper.RunningRecordMapper;
import com.alom.dorundorunbe.domain.runningrecord.repository.RunningRecordItemRepository;
import com.alom.dorundorunbe.domain.runningrecord.repository.RunningRecordRepository;
import com.alom.dorundorunbe.domain.user.domain.User;
import com.alom.dorundorunbe.domain.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RunningRecordService {
    private final RunningRecordRepository runningRecordRepository;
    private final RunningRecordMapper runningRecordMapper;
    private final UserService userService;
    private final ItemService itemService;
    private final RunningRecordItemRepository runningRecordItemRepository;

    public RunningRecordResponseDto saveStartRecord(RunningRecordStartRequestDto startRequestDto){
        User user = userService.findById(startRequestDto.getUserId());
        RunningRecord runningRecord = runningRecordMapper.toEntityFromStartRequestDto(startRequestDto);
        runningRecord.setUser(user);
        return runningRecordMapper.toResponseDto(runningRecordRepository.save(runningRecord));
    }

    public RunningRecordResponseDto saveEndRecord(Long id, RunningRecordEndRequestDto endRequestDto){
        RunningRecord runningRecord = runningRecordRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Running record with id "+id+" does not exist"));
        runningRecordMapper.updateEntityFromEndRequestDto(runningRecord, endRequestDto);
        List<EquippedItemResponseDto> equippedItems = itemService.findEquippedItem(runningRecord.getUser().getId());
        runningRecordRepository.save(runningRecord);
        equippedItems.forEach(itemDto -> {
            Item item = itemService.findItemById(itemDto.itemId());
            RunningRecordItem runningRecordItem = RunningRecordItem.builder()
                    .item(item)
                    .runningRecord(runningRecord)
                    .build();
            runningRecord.addRunningRecordItem(runningRecordItem);
        });
        runningRecordItemRepository.saveAll(runningRecord.getItems());

        return runningRecordMapper.toResponseDto(runningRecord);
    }

    // user별 최신순 특정 개수의 기록 조회
    public Page<RunningRecordResponseDto> findRunningRecords(Long userId, Pageable pageable){
        User user = userService.findById(userId);
        Page<RunningRecord> records = runningRecordRepository.findByUser(user, pageable);
        return records.map(runningRecordMapper::toResponseDto);
    }

    // 특정 기록의 상세 조회
    public RunningRecordResponseDto findRunningRecord(Long id){
        return runningRecordMapper.toResponseDto(runningRecordRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Running record with id "+id+" does not exist")));
    }
}
