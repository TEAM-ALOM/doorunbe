package com.alom.dorundorunbe.domain.runningrecord.dto;

import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RunningRecordEndRequestDto {
    private Double distance;

    private Integer cadence;

    private Integer elapsedTime;

    private String endTime;

    private Double averageSpeed;
}
