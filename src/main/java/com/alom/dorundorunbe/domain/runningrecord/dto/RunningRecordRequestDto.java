package com.alom.dorundorunbe.domain.runningrecord.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RunningRecordRequestDto {
    private Long userId;

    private Double distance;

    private Integer cadence;

    private Integer elapsedTime;

    private String startTime;

    private String endTime;

    private String date;

    private Double averageSpeed;

    private List<GpsCoordinateDto> gpsCoordinates = new ArrayList<>();
}
