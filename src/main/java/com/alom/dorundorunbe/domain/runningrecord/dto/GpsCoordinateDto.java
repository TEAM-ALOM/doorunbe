package com.alom.dorundorunbe.domain.runningrecord.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GpsCoordinateDto {
    private Double latitude;
    private Double longitude;
    private String timestamp;
}
