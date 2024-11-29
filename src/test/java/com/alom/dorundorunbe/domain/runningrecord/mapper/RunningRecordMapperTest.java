package com.alom.dorundorunbe.domain.runningrecord.mapper;

import com.alom.dorundorunbe.domain.runningrecord.domain.RunningRecord;
import com.alom.dorundorunbe.domain.runningrecord.dto.RunningRecordEndRequestDto;
import com.alom.dorundorunbe.domain.runningrecord.dto.RunningRecordResponseDto;
import com.alom.dorundorunbe.domain.runningrecord.dto.RunningRecordStartRequestDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class RunningRecordMapperTest {

    private final RunningRecordMapper mapper = new RunningRecordMapperImpl();

    @Test
    void testToResponseDto(){
        // given
        RunningRecord runningRecord = RunningRecord.builder()
                .id(1L)
                .distance(5.02)
                .cadence(150)
                .elapsedTime(2038)
                .speed(8.86)
                .isFinished(true)
                .date(LocalDate.of(2024, 10, 30))
                .startTime(LocalDateTime.of(2024, 10, 30, 8, 0, 0))
                .endTime(LocalDateTime.of(2024, 10, 30, 8, 33, 58))
                .build();

        // when
        RunningRecordResponseDto responseDto = mapper.toResponseDto(runningRecord);

        // then
        assertThat(responseDto).isNotNull();
        assertThat(responseDto.getId()).isEqualTo(1L);
        assertThat(responseDto.getDistance()).isEqualTo(5.02);
        assertThat(responseDto.getCadence()).isEqualTo(150);
        assertThat(responseDto.getElapsedTime()).isEqualTo(2038);
        assertThat(responseDto.getSpeed()).isEqualTo(8.86);
        assertThat(responseDto.getIsFinished()).isTrue();
        assertThat(responseDto.getDate()).isEqualTo("2024-10-30");
        assertThat(responseDto.getStartTime()).isEqualTo("2024-10-30T08:00:00");
        assertThat(responseDto.getEndTime()).isEqualTo("2024-10-30T08:33:58");
    }

    @Test
    void testToEntityFromStartRequestDto(){
        // given
        RunningRecordStartRequestDto startRequestDto = RunningRecordStartRequestDto.builder()
                .date("2024-10-30")
                .startTime("2024-10-30T08:00:00")
                .build();

        // when
        RunningRecord runningRecord = mapper.toEntityFromStartRequestDto(startRequestDto);

        // then
        assertThat(runningRecord).isNotNull();
        assertThat(runningRecord.getDate()).isEqualTo(LocalDate.of(2024, 10, 30));
        assertThat(runningRecord.getStartTime()).isEqualTo(LocalDateTime.of(2024, 10, 30, 8, 0, 0));
    }

    @Test
    void testUpdateEntityFromEndRequestDto() {
        // given
        RunningRecord runningRecord = RunningRecord.builder()
                .distance(5.02)
                .cadence(150)
                .elapsedTime(2038)
                .speed(8.86)
                .isFinished(false)
                .endTime(null)
                .build();

        RunningRecordEndRequestDto endRequestDto = RunningRecordEndRequestDto.builder()
                .distance(5.02)
                .cadence(150)
                .elapsedTime(2038)
                .speed(8.86)
                .endTime("2024-10-30T08:33:58")
                .build();

        // when
        mapper.updateEntityFromEndRequestDto(runningRecord, endRequestDto);

        // then
        assertThat(runningRecord.getDistance()).isEqualTo(5.02);
        assertThat(runningRecord.getCadence()).isEqualTo(150);
        assertThat(runningRecord.getElapsedTime()).isEqualTo(2038);
        assertThat(runningRecord.getSpeed()).isEqualTo(8.86);
        assertThat(runningRecord.getEndTime()).isEqualTo(LocalDateTime.of(2024, 10, 30, 8, 33, 58));
        assertThat(runningRecord.getIsFinished()).isTrue();
    }
}
