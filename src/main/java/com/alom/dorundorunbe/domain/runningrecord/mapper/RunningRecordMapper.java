package com.alom.dorundorunbe.domain.runningrecord.mapper;

import com.alom.dorundorunbe.domain.item.dto.EquippedItemResponseDto;
import com.alom.dorundorunbe.domain.runningrecord.domain.GpsCoordinate;
import com.alom.dorundorunbe.domain.runningrecord.domain.RunningRecord;
import com.alom.dorundorunbe.domain.runningrecord.domain.RunningRecordItem;
import com.alom.dorundorunbe.domain.runningrecord.dto.GpsCoordinateDto;
import com.alom.dorundorunbe.domain.runningrecord.dto.RunningRecordRequestDto;
import com.alom.dorundorunbe.domain.runningrecord.dto.RunningRecordResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Mapper(componentModel = "spring", imports = {LocalDateTime.class, LocalDate.class, DateTimeFormatter.class})
public interface RunningRecordMapper {
    @Mapping(target = "startTime", expression = "java(toStringDateTime(runningRecord.getStartTime()))")
    @Mapping(target = "endTime", expression = "java(toStringDateTime(runningRecord.getEndTime()))")
    @Mapping(target = "date", expression = "java(toStringDate(runningRecord.getDate()))")
    @Mapping(target = "items", expression = "java(mapItems(runningRecord.getItems()))")
    @Mapping(target = "gpsCoordinates", expression = "java(mapGpsCoordinates(runningRecord.getGpsCoordinates()))")
    RunningRecordResponseDto toResponseDto(RunningRecord runningRecord);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pace", ignore = true)
    @Mapping(target = "gpsCoordinates", ignore = true)
    @Mapping(target = "startTime", expression = "java(toLocalDateTime(requestDto.getStartTime()))")
    @Mapping(target = "endTime", expression = "java(toLocalDateTime(requestDto.getEndTime()))")
    @Mapping(target = "date", expression = "java(toLocalDate(requestDto.getDate()))")
    RunningRecord toEntityFromRequestDto(RunningRecordRequestDto requestDto);

    default String toStringDate(LocalDate date){
        return date != null ? date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : null;
    }

    default String toStringDateTime(LocalDateTime dateTime){
        if (dateTime != null) {
            return dateTime.atZone(ZoneOffset.UTC)
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"));
        }
        return null;
    }
    default LocalDate toLocalDate(String dateString) {
        return dateString != null ? LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd")) : null;
    }

    default LocalDateTime toLocalDateTime(String dateTimeString) {
        if (dateTimeString != null) {
            OffsetDateTime offsetDateTime = OffsetDateTime.parse(dateTimeString, DateTimeFormatter.ISO_DATE_TIME);
            return offsetDateTime.toLocalDateTime();
        }
        return null;
    }

    default List<EquippedItemResponseDto> mapItems(List<RunningRecordItem> runningRecordItems){
        return runningRecordItems == null ? List.of() : runningRecordItems.stream()
                .map(item -> EquippedItemResponseDto.of(
                        item.getItem().getId(),
                        item.getItem().getName(),
                        item.getItem().getItemCategory()
                )).toList();
    }

    default List<GpsCoordinateDto> mapGpsCoordinates(List<GpsCoordinate> gpsCoordinates) {
        return gpsCoordinates == null ? List.of() : gpsCoordinates.stream()
                .map(coord -> new GpsCoordinateDto(
                        coord.getLatitude(),
                        coord.getLongitude(),
                        coord.getTimestamp().atZone(ZoneOffset.UTC)
                                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"))
                )).toList();
    }

}
