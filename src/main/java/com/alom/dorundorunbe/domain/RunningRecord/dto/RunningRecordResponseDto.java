package com.alom.dorundorunbe.domain.RunningRecord.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RunningRecordResponseDto {
    private Long id;

    private LocalDateTime date;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private double distance;

    private int cadence;

    private long elapsedTime;

    private double speed;

    private boolean isFinished;
}
