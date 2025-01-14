package com.alom.dorundorunbe.domain.runningrecord.dto;

import com.alom.dorundorunbe.domain.item.dto.EquippedItemResponseDto;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RunningRecordResponseDto {
    private Long id;

    private String startTime;

    private String endTime;

    private String date;

    private Double distance;

    private Integer cadence;

    private Integer elapsedTime;

    private Double averageSpeed;

    private Double pace;

    private List<EquippedItemResponseDto> items = new ArrayList<>();

    private List<GpsCoordinateDto> gpsCoordinates = new ArrayList<>();
}
