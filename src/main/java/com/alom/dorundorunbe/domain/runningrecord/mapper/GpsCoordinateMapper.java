package com.alom.dorundorunbe.domain.runningrecord.mapper;

import com.alom.dorundorunbe.domain.runningrecord.domain.GpsCoordinate;
import com.alom.dorundorunbe.domain.runningrecord.dto.GpsCoordinateDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring")

public interface GpsCoordinateMapper {
    @Mapping(target = "timestamp", expression = "java(toLocalDateTime(gpsCoordinateDto.getTimestamp()))")
    GpsCoordinate toEntity(GpsCoordinateDto gpsCoordinateDto);


    default LocalDateTime toLocalDateTime(String dateTimeString) {
        if (dateTimeString != null) {
            OffsetDateTime offsetDateTime = OffsetDateTime.parse(dateTimeString, DateTimeFormatter.ISO_DATE_TIME);
            return offsetDateTime.toLocalDateTime();
        }
        return null;
    }
}
