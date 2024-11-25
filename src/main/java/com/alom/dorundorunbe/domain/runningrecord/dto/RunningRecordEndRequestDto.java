package com.alom.dorundorunbe.domain.runningrecord.dto;

import jakarta.persistence.Entity;
import lombok.*;

import java.time.LocalDateTime;


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

    private Double speed;
}
