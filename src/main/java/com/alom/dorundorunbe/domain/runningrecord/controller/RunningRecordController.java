package com.alom.dorundorunbe.domain.runningrecord.controller;

import com.alom.dorundorunbe.domain.runningrecord.dto.RunningRecordEndRequestDto;
import com.alom.dorundorunbe.domain.runningrecord.dto.RunningRecordResponseDto;
import com.alom.dorundorunbe.domain.runningrecord.dto.RunningRecordStartRequestDto;
import com.alom.dorundorunbe.domain.runningrecord.service.RunningRecordService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/records")
public class RunningRecordController {
    private final RunningRecordService runningRecordService;

    @PostMapping
    public ResponseEntity<RunningRecordResponseDto> createRunningRecord(@RequestBody RunningRecordStartRequestDto startRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(runningRecordService.saveStartRecord(startRequestDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RunningRecordResponseDto> updateRunningRecord(@PathVariable(name = "id") Long id, @RequestBody RunningRecordEndRequestDto endRequestDto){
        return ResponseEntity.ok(runningRecordService.saveEndRecord(id, endRequestDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RunningRecordResponseDto> fetchRunningRecord(@PathVariable(name = "id") Long id){
        return ResponseEntity.ok(runningRecordService.findRunningRecord(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<RunningRecordResponseDto>> fetchRunningRecords(@PathVariable(name = "userId") Long userId,
                                                                                     @RequestParam(defaultValue = "0") int page,
                                                                                     @RequestParam(defaultValue = "5") int size){
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(runningRecordService.findRunningRecords(userId, pageable));
    }


}
