package com.alom.dorundorunbe.domain.runningrecord.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "GpsCoordinate 요청/응답 DTO")
public class GpsCoordinateDto {
    @Schema(description = "위도", example = "37.7749")
    private Double latitude;

    @Schema(description = "경도", example = "-122.4194")
    private Double longitude;

    @Schema(description = "좌표 측정 시간", example = "2025-01-01T08:00:00Z")
    private String timestamp;
}
