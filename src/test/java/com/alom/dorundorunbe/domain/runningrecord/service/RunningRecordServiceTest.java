package com.alom.dorundorunbe.domain.runningrecord.service;

import com.alom.dorundorunbe.domain.item.domain.Item;
import com.alom.dorundorunbe.domain.item.domain.ItemCategory;
import com.alom.dorundorunbe.domain.item.dto.EquippedItemResponseDto;
import com.alom.dorundorunbe.domain.item.service.ItemService;
import com.alom.dorundorunbe.domain.runningrecord.domain.RunningRecord;
import com.alom.dorundorunbe.domain.runningrecord.domain.RunningRecordItem;
import com.alom.dorundorunbe.domain.runningrecord.dto.RunningRecordRequestDto;
import com.alom.dorundorunbe.domain.runningrecord.dto.RunningRecordResponseDto;
import com.alom.dorundorunbe.domain.runningrecord.dto.RunningRecordStartRequestDto;
import com.alom.dorundorunbe.domain.runningrecord.mapper.RunningRecordMapper;
import com.alom.dorundorunbe.domain.runningrecord.repository.RunningRecordItemRepository;
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
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
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

    @Mock
    private ItemService itemService;

    @Mock
    private RunningRecordItemRepository runningRecordItemRepository;

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
        responseDto.setItems(List.of());

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
        assertEquals(List.of(), responseDto.getItems());
    }

    @Test
    void saveEndRecord(){
        // given
        Long id = 1L;
        Long userId = 10L;

        RunningRecord runningRecord = new RunningRecord();
        runningRecord.setId(id);
        User user = new User();
        user.setId(userId);
        runningRecord.setUser(user);

        RunningRecordRequestDto endRequestDto = new RunningRecordRequestDto();
        RunningRecordResponseDto responseDto = new RunningRecordResponseDto();

        EquippedItemResponseDto equippedItem1 = new EquippedItemResponseDto(1L, "Item1", ItemCategory.ACCESSORY);
        EquippedItemResponseDto equippedItem2 = new EquippedItemResponseDto(2L, "Item2", ItemCategory.HAIR);
        List<EquippedItemResponseDto> equippedItems = List.of(equippedItem1, equippedItem2);

        Item item1 = Item.builder().id(1L).build();
        Item item2 = Item.builder().id(2L).build();

        RunningRecordItem runningRecordItem1 = RunningRecordItem.builder().runningRecord(runningRecord).item(item1).build();
        RunningRecordItem runningRecordItem2 = RunningRecordItem.builder().runningRecord(runningRecord).item(item2).build();

        when(runningRecordRepository.findById(id)).thenReturn(Optional.of(runningRecord));
        doNothing().when(runningRecordMapper).updateEntityFromEndRequestDto(runningRecord, endRequestDto);
        when(itemService.findEquippedItem(userId)).thenReturn(equippedItems);
        when(itemService.findItemById(1L)).thenReturn(item1);
        when(itemService.findItemById(2L)).thenReturn(item2);
        when(runningRecordMapper.toResponseDto(runningRecord)).thenReturn(responseDto);

        // when
        RunningRecordResponseDto result = runningRecordService.saveEndRecord(id, endRequestDto);

        // then
        verify(runningRecordRepository).findById(id);
        verify(runningRecordMapper).updateEntityFromEndRequestDto(runningRecord, endRequestDto);
        verify(itemService).findEquippedItem(userId);
        verify(itemService).findItemById(1L);
        verify(itemService).findItemById(2L);
        verify(runningRecordItemRepository).saveAll(runningRecord.getItems());
        verify(runningRecordRepository).save(runningRecord);

        assertEquals(result, responseDto);
        assertEquals(2, runningRecord.getItems().size());
        assertEquals(item1, runningRecord.getItems().get(0).getItem());
        assertEquals(item2, runningRecord.getItems().get(1).getItem());
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
        responseDto.setItems(List.of(new EquippedItemResponseDto(1L, "Item1", ItemCategory.ACCESSORY)));

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
        assertEquals(1, result.getContent().get(0).getItems().size());
        assertEquals(1L, result.getContent().get(0).getItems().get(0).itemId());
    }

    @Test
    void findRunningRecord(){
        // given
        Long id = 1L;
        RunningRecord runningRecord = new RunningRecord();
        RunningRecordResponseDto responseDto = new RunningRecordResponseDto();
        responseDto.setItems(List.of(new EquippedItemResponseDto(1L, "Item1", ItemCategory.ACCESSORY))); // 리스트 초기화

        when(runningRecordRepository.findById(id)).thenReturn(Optional.of(runningRecord));
        when(runningRecordMapper.toResponseDto(runningRecord)).thenReturn(responseDto);

        // when
        RunningRecordResponseDto result = runningRecordService.findRunningRecord(id);

        // then
        verify(runningRecordRepository).findById(id);
        assertEquals(result, responseDto);
        assertEquals(1, result.getItems().size());
        assertEquals(1L, result.getItems().get(0).itemId());
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
