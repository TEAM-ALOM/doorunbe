package com.alom.dorundorunbe.domain.runningrecord.mapper;

import com.alom.dorundorunbe.domain.runningrecord.domain.RunningRecord;
import com.alom.dorundorunbe.domain.runningrecord.dto.RunningRecordEndRequestDto;
import com.alom.dorundorunbe.domain.runningrecord.dto.RunningRecordResponseDto;
import com.alom.dorundorunbe.domain.runningrecord.dto.RunningRecordStartRequestDto;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-13T16:52:28+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.13 (Amazon.com Inc.)"
)
@Component
public class RunningRecordMapperImpl implements RunningRecordMapper {

    @Override
    public RunningRecordResponseDto toResponseDto(RunningRecord runningRecord) {
        if ( runningRecord == null ) {
            return null;
        }

        RunningRecordResponseDto.RunningRecordResponseDtoBuilder runningRecordResponseDto = RunningRecordResponseDto.builder();

        runningRecordResponseDto.id( runningRecord.getId() );
        runningRecordResponseDto.distance( runningRecord.getDistance() );
        runningRecordResponseDto.cadence( runningRecord.getCadence() );
        runningRecordResponseDto.elapsedTime( runningRecord.getElapsedTime() );
        runningRecordResponseDto.speed( runningRecord.getSpeed() );
        runningRecordResponseDto.isFinished( runningRecord.getIsFinished() );

        runningRecordResponseDto.date( toStringDate(runningRecord.getDate()) );
        runningRecordResponseDto.startTime( toStringDateTime(runningRecord.getStartTime()) );
        runningRecordResponseDto.endTime( toStringDateTime(runningRecord.getEndTime()) );
        runningRecordResponseDto.items( mapItems(runningRecord.getItems()) );

        return runningRecordResponseDto.build();
    }

    @Override
    public RunningRecord toEntityFromStartRequestDto(RunningRecordStartRequestDto startRequestDto) {
        if ( startRequestDto == null ) {
            return null;
        }

        RunningRecord.RunningRecordBuilder runningRecord = RunningRecord.builder();

        runningRecord.date( toLocalDate(startRequestDto.getDate()) );
        runningRecord.startTime( toLocalDateTime(startRequestDto.getStartTime()) );

        return runningRecord.build();
    }

    @Override
    public void updateEntityFromEndRequestDto(RunningRecord runningRecord, RunningRecordEndRequestDto endRequestDto) {
        if ( endRequestDto == null ) {
            return;
        }

        runningRecord.setDistance( endRequestDto.getDistance() );
        runningRecord.setCadence( endRequestDto.getCadence() );
        runningRecord.setElapsedTime( endRequestDto.getElapsedTime() );
        runningRecord.setSpeed( endRequestDto.getSpeed() );

        runningRecord.setEndTime( toLocalDateTime(endRequestDto.getEndTime()) );
        runningRecord.setIsFinished( true );
    }
}
