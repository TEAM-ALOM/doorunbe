package com.alom.dorundorunbe.domain.runningrecord.mapper;

import com.alom.dorundorunbe.domain.runningrecord.domain.RunningRecord;
import com.alom.dorundorunbe.domain.runningrecord.dto.RunningRecordEndRequestDto;
import com.alom.dorundorunbe.domain.runningrecord.dto.RunningRecordResponseDto;
import com.alom.dorundorunbe.domain.runningrecord.dto.RunningRecordStartRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring", imports = {LocalDateTime.class, LocalDate.class, DateTimeFormatter.class})
public interface RunningRecordMapper {
    @Mapping(target = "date", expression = "java(toStringDate(runningRecord.getDate()))")
    @Mapping(target = "startTime", expression = "java(toStringDateTime(runningRecord.getStartTime()))")
    @Mapping(target = "endTime", expression = "java(toStringDateTime(runningRecord.getEndTime()))")
    RunningRecordResponseDto toResponseDto(RunningRecord runningRecord);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "date", expression = "java(toLocalDate(startRequestDto.getDate()))")
    @Mapping(target = "startTime", expression = "java(toLocalDateTime(startRequestDto.getStartTime()))")
    RunningRecord toEntityFromStartRequestDto(RunningRecordStartRequestDto startRequestDto);

//    @Mapping(target = "endTime", expression = "java(toLocalDateTime(endRequestDto.getEndTime()))")
//    RunningRecord toEntityFromEndRequestDto(RunningRecordEndRequestDto endRequestDto);

    @Mapping(target = "distance", source = "endRequestDto.distance")
    @Mapping(target = "cadence", source = "endRequestDto.cadence")
    @Mapping(target = "elapsedTime", source = "endRequestDto.elapsedTime")
    @Mapping(target = "endTime", expression = "java(toLocalDateTime(endRequestDto.getEndTime()))")
    @Mapping(target = "speed", source = "endRequestDto.speed")
    @Mapping(target = "isFinished", constant = "true")
    void updateEntityFromEndRequestDto(@MappingTarget RunningRecord runningRecord, RunningRecordEndRequestDto endRequestDto);

    default String toStringDate(LocalDate date){
        return date != null ? date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : null;
    }

    default String toStringDateTime(LocalDateTime dateTime){
        return dateTime != null ? dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")) : null;
    }
    default LocalDate toLocalDate(String dateString) {
        return dateString != null ? LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd")) : null;
    }

    default LocalDateTime toLocalDateTime(String dateTimeString) {
        return dateTimeString != null ? LocalDateTime.parse(dateTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")) : null;
    }

}
