package com.alom.dorundorunbe.domain.RunningRecord.controller;

import com.alom.dorundorunbe.domain.RunningRecord.dto.ResponseDto;
import com.alom.dorundorunbe.domain.RunningRecord.dto.RunningRecordEndRequestDto;
import com.alom.dorundorunbe.domain.RunningRecord.dto.RunningRecordResponseDto;
import com.alom.dorundorunbe.domain.RunningRecord.dto.RunningRecordStartRequestDto;
import com.alom.dorundorunbe.domain.RunningRecord.service.RunningRecordService;
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
    public ResponseEntity<ResponseDto<RunningRecordResponseDto>> createStartRunningRecord(RunningRecordStartRequestDto startRequestDto){
        RunningRecordResponseDto result =  runningRecordService.saveStartRecord(startRequestDto);
        ResponseDto<RunningRecordResponseDto> response = new ResponseDto<>();
        response.setSuccess(true);
        response.setCode(HttpStatus.CREATED.value());
        response.setMessage("러닝 시작 시간이 성공적으로 저장되었습니다.");
        response.setResult(result);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/end")
    public ResponseEntity<ResponseDto<RunningRecordResponseDto>> createEndRunningRecord(RunningRecordEndRequestDto endRequestDto){
        RunningRecordResponseDto result = runningRecordService.saveEndRecord(endRequestDto);
        ResponseDto<RunningRecordResponseDto> response = new ResponseDto<>();
        response.setSuccess(true);
        response.setCode(HttpStatus.CREATED.value());
        response.setMessage("러닝 기록이 성공적으로 생성되었습니다.");
        response.setResult(result);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<RunningRecordResponseDto>> fetchRunningRecord(@PathVariable Long id){
        RunningRecordResponseDto result = runningRecordService.findRunningRecord(id);
        ResponseDto<RunningRecordResponseDto> response = new ResponseDto<>();
        response.setSuccess(true);
        response.setCode(HttpStatus.OK.value());
        response.setMessage("요청에 성공하였습니다.");
        response.setResult(result);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ResponseDto<Page<RunningRecordResponseDto>>> fetchRunningRecords(@PathVariable Long userId,
                                                                                     @RequestParam(defaultValue = "0") int page,
                                                                                     @RequestParam(defaultValue = "5") int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<RunningRecordResponseDto> result = runningRecordService.findRunningRecords(userId, pageable);
        ResponseDto<Page<RunningRecordResponseDto>> response = new ResponseDto<>();
        response.setSuccess(true);
        response.setCode(HttpStatus.OK.value());
        response.setMessage("요청에 성공하였습니다.");
        response.setResult(result);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
