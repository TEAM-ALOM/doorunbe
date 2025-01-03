package com.alom.dorundorunbe.domain.RunningRecord.mapper;

import com.alom.dorundorunbe.domain.RunningRecord.domain.RunningRecord;
import com.alom.dorundorunbe.domain.RunningRecord.dto.RunningRecordEndRequestDto;
import com.alom.dorundorunbe.domain.RunningRecord.dto.RunningRecordResponseDto;
import com.alom.dorundorunbe.domain.RunningRecord.dto.RunningRecordStartRequestDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-03T11:02:42+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.8 (Oracle Corporation)"
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
        runningRecordResponseDto.date( runningRecord.getDate() );
        runningRecordResponseDto.startTime( runningRecord.getStartTime() );
        runningRecordResponseDto.endTime( runningRecord.getEndTime() );
        runningRecordResponseDto.distance( runningRecord.getDistance() );
        runningRecordResponseDto.cadence( runningRecord.getCadence() );
        runningRecordResponseDto.elapsedTime( runningRecord.getElapsedTime() );
        runningRecordResponseDto.speed( runningRecord.getSpeed() );

        return runningRecordResponseDto.build();
    }

    @Override
    public RunningRecord toEntityFromStartRequestDto(RunningRecordStartRequestDto startRequestDto) {
        if ( startRequestDto == null ) {
            return null;
        }

        RunningRecord.RunningRecordBuilder runningRecord = RunningRecord.builder();

        runningRecord.date( startRequestDto.getDate() );
        runningRecord.startTime( startRequestDto.getStartTime() );

        return runningRecord.build();
    }

    @Override
    public RunningRecord toEntityFromEndRequestDto(RunningRecordEndRequestDto endRequestDto) {
        if ( endRequestDto == null ) {
            return null;
        }

        RunningRecord.RunningRecordBuilder runningRecord = RunningRecord.builder();

        runningRecord.id( endRequestDto.getId() );
        runningRecord.endTime( endRequestDto.getEndTime() );
        runningRecord.distance( endRequestDto.getDistance() );
        runningRecord.cadence( endRequestDto.getCadence() );
        runningRecord.elapsedTime( endRequestDto.getElapsedTime() );
        runningRecord.speed( endRequestDto.getSpeed() );

        return runningRecord.build();
    }
}
