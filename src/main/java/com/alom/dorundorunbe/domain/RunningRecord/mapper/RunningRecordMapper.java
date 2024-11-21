package com.alom.dorundorunbe.domain.RunningRecord.mapper;

import com.alom.dorundorunbe.domain.RunningRecord.domain.RunningRecord;
import com.alom.dorundorunbe.domain.RunningRecord.dto.RunningRecordEndRequestDto;
import com.alom.dorundorunbe.domain.RunningRecord.dto.RunningRecordResponseDto;
import com.alom.dorundorunbe.domain.RunningRecord.dto.RunningRecordStartRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RunningRecordMapper {
    RunningRecordResponseDto toResponseDto(RunningRecord runningRecord);
    @Mapping(target = "id", ignore = true)
    RunningRecord toEntityFromStartRequestDto(RunningRecordStartRequestDto startRequestDto);
    RunningRecord toEntityFromEndRequestDto(RunningRecordEndRequestDto endRequestDto);

}
