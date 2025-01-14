package com.alom.dorundorunbe.domain.runningrecord.mapper;

import com.alom.dorundorunbe.domain.runningrecord.domain.GpsCoordinate;
import com.alom.dorundorunbe.domain.runningrecord.dto.GpsCoordinateDto;
import org.mapstruct.Mapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring")

public interface GpsCoordinateMapper {
    GpsCoordinate toEntity(GpsCoordinateDto gpsCoordinateDto);

    GpsCoordinateDto toDto(GpsCoordinate gpsCoordinate);
}
