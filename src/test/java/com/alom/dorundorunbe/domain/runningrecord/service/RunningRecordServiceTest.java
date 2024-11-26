package com.alom.dorundorunbe.domain.runningrecord.service;

import com.alom.dorundorunbe.domain.runningrecord.domain.RunningRecord;
import com.alom.dorundorunbe.domain.runningrecord.dto.RunningRecordEndRequestDto;
import com.alom.dorundorunbe.domain.runningrecord.dto.RunningRecordResponseDto;
import com.alom.dorundorunbe.domain.runningrecord.dto.RunningRecordStartRequestDto;
import com.alom.dorundorunbe.domain.runningrecord.mapper.RunningRecordMapper;
import com.alom.dorundorunbe.domain.runningrecord.repository.RunningRecordRepository;
import com.alom.dorundorunbe.domain.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.alom.dorundorunbe.domain.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class RunningRecordServiceTest {

    @Mock
    private RunningRecordRepository runningRecordRepository;

    @Mock
    private RunningRecordMapper runningRecordMapper;

    @Mock
    private UserService userService;

    @InjectMocks
    private RunningRecordService runningRecordService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveStartRecord() {
        // given
        Long userId = 1L;
        RunningRecordStartRequestDto startRequestDto = new RunningRecordStartRequestDto();
        startRequestDto.setUserId(userId);

        User user = new User();
        RunningRecord runningRecord = new RunningRecord();
        RunningRecordResponseDto responseDto = new RunningRecordResponseDto();

        when(userService.findById(userId)).thenReturn(user);
        when(runningRecordMapper.toEntityFromStartRequestDto(startRequestDto)).thenReturn(runningRecord);
        when(runningRecordRepository.save(runningRecord)).thenReturn(runningRecord);
        when(runningRecordMapper.toResponseDto(runningRecord)).thenReturn(responseDto);

        // when
        RunningRecordResponseDto result = runningRecordService.saveStartRecord(startRequestDto);

        // then
        verify(userService).findById(userId);
        verify(runningRecordRepository).save(runningRecord);
        assertEquals(responseDto, result);
    }

    @Test
    void saveEndRecord(){
        // given
        Long id = 1L;
        RunningRecord runningRecord = new RunningRecord();
        runningRecord.setId(id);

        RunningRecordEndRequestDto endRequestDto = new RunningRecordEndRequestDto();
        RunningRecordResponseDto responseDto = new RunningRecordResponseDto();

        when(runningRecordRepository.findById(id)).thenReturn(Optional.of(runningRecord));
        doNothing().when(runningRecordMapper).updateEntityFromEndRequestDto(runningRecord, endRequestDto);
        when(runningRecordMapper.toResponseDto(runningRecord)).thenReturn(responseDto);

        // when
        RunningRecordResponseDto result = runningRecordService.saveEndRecord(id, endRequestDto);

        // then
        verify(runningRecordRepository).findById(id);
        assertEquals(result, responseDto);
    }

    @Test
    void findRunningRecords(){
        // given
        Long userId = 1L;
        Pageable pageable = mock(Pageable.class);
        User user = new User();

        RunningRecord runningRecord = new RunningRecord();
        Page<RunningRecord> records = new PageImpl<>(Collections.singletonList(runningRecord));
        RunningRecordResponseDto responseDto = new RunningRecordResponseDto();

        when(userService.findById(userId)).thenReturn(user);
        when(runningRecordRepository.findByUser(user, pageable)).thenReturn(records);
        when(runningRecordMapper.toResponseDto(runningRecord)).thenReturn(responseDto);

        // when
        Page<RunningRecordResponseDto> result = runningRecordService.findRunningRecords(userId, pageable);

        // then
        verify(userService).findById(userId);
        verify(runningRecordRepository).findByUser(user, pageable);
        assertEquals(1,result.getTotalElements());
        assertEquals(responseDto, result.getContent().get(0));
    }

    @Test
    void findRunningRecord(){
        // given
        Long id = 1L;
        RunningRecord runningRecord = new RunningRecord();
        RunningRecordResponseDto responseDto = new RunningRecordResponseDto();

        when(runningRecordRepository.findById(id)).thenReturn(Optional.of(runningRecord));
        when(runningRecordMapper.toResponseDto(runningRecord)).thenReturn(responseDto);

        // when
        RunningRecordResponseDto result = runningRecordService.findRunningRecord(id);

        // then
        verify(runningRecordRepository).findById(id);
        assertEquals(result, responseDto);
    }

    @Test
    void findRunningRecord_NotFound(){
        // given
        Long id = 1L;
        when(runningRecordRepository.findById(id)).thenReturn(Optional.empty());

        // when / then
        assertThrows(IllegalStateException.class,() -> runningRecordService.findRunningRecord(id));
        verify(runningRecordRepository).findById(id);
    }
}
