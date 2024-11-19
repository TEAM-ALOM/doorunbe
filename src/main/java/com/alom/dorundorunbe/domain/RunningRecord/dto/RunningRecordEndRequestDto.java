package com.alom.dorundorunbe.domain.RunningRecord.dto;

import jakarta.persistence.Entity;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RunningRecordEndRequestDto {
    private Long id;

    private double distance;

    private int cadence;

    private long elapsedTime;

    private LocalDateTime endTime;

    private double speed;
}
