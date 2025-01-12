package com.alom.dorundorunbe.domain.runningrecord.controller;

import com.alom.dorundorunbe.domain.item.domain.ItemCategory;
import com.alom.dorundorunbe.domain.item.dto.EquippedItemResponseDto;
import com.alom.dorundorunbe.domain.runningrecord.dto.RunningRecordEndRequestDto;
import com.alom.dorundorunbe.domain.runningrecord.dto.RunningRecordResponseDto;
import com.alom.dorundorunbe.domain.runningrecord.dto.RunningRecordStartRequestDto;
import com.alom.dorundorunbe.domain.runningrecord.service.RunningRecordService;
import com.alom.dorundorunbe.global.util.config.SecurityConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RunningRecordController.class)
@Import(SecurityConfig.class)
public class RunningRecordControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RunningRecordService runningRecordService;

    @Test
    public void testCreateRunningRecord() throws Exception {
        RunningRecordResponseDto responseDto = new RunningRecordResponseDto(
                1L,
                "2024-10-30",
                "2024-10-30T08:00:00",
                null,
                null,
                null,
                null,
                null,
                false,
                List.of()
        );
        when(runningRecordService.saveStartRecord(any(RunningRecordStartRequestDto.class))).thenReturn(responseDto);

        String requestBody = objectMapper.writeValueAsString(new RunningRecordStartRequestDto(1L, "2024-10-30", "2024-10-30T08:00:00"));

        mockMvc.perform(post("/records")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .with(httpBasic("user", "password")))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.date").value("2024-10-30"))
                .andExpect(jsonPath("$.startTime").value("2024-10-30T08:00:00"));
    }

    @Test
    public void testUpdateRunningRecord() throws Exception {
        RunningRecordResponseDto responseDto = new RunningRecordResponseDto(
                1L,
                "2024-10-30",
                "2024-10-30T08:00:00",
                "2024-10-30T08:33:58",
                5.02,
                150,
                2038,
                8.86,
                true,
                List.of(new EquippedItemResponseDto(1L, "Item1", ItemCategory.ACCESSORY))
        );
        when(runningRecordService.saveEndRecord(eq(1L), any(RunningRecordEndRequestDto.class))).thenReturn(responseDto);

        String requestBody = objectMapper.writeValueAsString(new RunningRecordEndRequestDto(5.02, 150, 2038, "2024-10-30T08:33:58", 8.86));

        mockMvc.perform(put("/records/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .with(httpBasic("user", "password")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.date").value("2024-10-30"))
                .andExpect(jsonPath("$.startTime").value("2024-10-30T08:00:00"))
                .andExpect(jsonPath("$.endTime").value("2024-10-30T08:33:58"))
                .andExpect(jsonPath("$.distance").value(5.02))
                .andExpect(jsonPath("$.cadence").value(150))
                .andExpect(jsonPath("$.elapsedTime").value(2038))
                .andExpect(jsonPath("$.speed").value(8.86))
                .andExpect(jsonPath("$.isFinished").value(true))
                .andExpect(jsonPath("$.items[0].itemId").value(1L))
                .andExpect(jsonPath("$.items[0].name").value("Item1"))
                .andExpect(jsonPath("$.items[0].itemCategory").value("ACCESSORY"));
    }

    @Test
    public void testFetchRunningRecord() throws Exception {
        RunningRecordResponseDto responseDto = new RunningRecordResponseDto(
                1L,
                "2024-10-30",
                "2024-10-30T08:00:00",
                "2024-10-30T08:33:58",
                5.02,
                150,
                2038,
                8.86,
                true,
                List.of(new EquippedItemResponseDto(1L, "Item1", ItemCategory.ACCESSORY))
        );
        when(runningRecordService.findRunningRecord(1L)).thenReturn(responseDto);

        mockMvc.perform(get("/records/1")
                .with(httpBasic("user", "password")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.date").value("2024-10-30"))
                .andExpect(jsonPath("$.startTime").value("2024-10-30T08:00:00"))
                .andExpect(jsonPath("$.endTime").value("2024-10-30T08:33:58"))
                .andExpect(jsonPath("$.distance").value(5.02))
                .andExpect(jsonPath("$.cadence").value(150))
                .andExpect(jsonPath("$.elapsedTime").value(2038))
                .andExpect(jsonPath("$.speed").value(8.86))
                .andExpect(jsonPath("$.isFinished").value(true))
                .andExpect(jsonPath("$.items[0].itemId").value(1L))
                .andExpect(jsonPath("$.items[0].name").value("Item1"))
                .andExpect(jsonPath("$.items[0].itemCategory").value("ACCESSORY"));
    }

    @Test
    public void testFetchRunningRecords() throws Exception {
        RunningRecordResponseDto responseDto = new RunningRecordResponseDto(
                1L,
                "2024-10-30",
                "2024-10-30T08:00:00",
                "2024-10-30T08:33:58",
                5.02,
                150,
                2038,
                8.86,
                true,
                List.of(new EquippedItemResponseDto(1L, "Item1", ItemCategory.ACCESSORY))
        );
        Page<RunningRecordResponseDto> responsePage = new PageImpl<>(Collections.singletonList(responseDto));
        when(runningRecordService.findRunningRecords(eq(1L), any(Pageable.class))).thenReturn(responsePage);

        mockMvc.perform(get("/records/user/1")
                .with(httpBasic("user", "password"))
                .param("page", "0")
                .param("size", "5"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1L))
                .andExpect(jsonPath("$.content[0].date").value("2024-10-30"))
                .andExpect(jsonPath("$.content[0].startTime").value("2024-10-30T08:00:00"))
                .andExpect(jsonPath("$.content[0].endTime").value("2024-10-30T08:33:58"))
                .andExpect(jsonPath("$.content[0].distance").value(5.02))
                .andExpect(jsonPath("$.content[0].cadence").value(150))
                .andExpect(jsonPath("$.content[0].elapsedTime").value(2038))
                .andExpect(jsonPath("$.content[0].speed").value(8.86))
                .andExpect(jsonPath("$.content[0].isFinished").value(true))
                .andExpect(jsonPath("$.content[0].items[0].itemId").value(1L))
                .andExpect(jsonPath("$.content[0].items[0].name").value("Item1"))
                .andExpect(jsonPath("$.content[0].items[0].itemCategory").value("ACCESSORY"));
    }

}
