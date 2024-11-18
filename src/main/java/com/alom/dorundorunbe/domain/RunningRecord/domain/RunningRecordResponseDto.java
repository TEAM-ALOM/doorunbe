package com.alom.dorundorunbe.domain.RunningRecord.domain;

import java.time.LocalDateTime;

public class RunningRecordResponseDto {
    private Long id;

    private LocalDateTime date;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private double distance;

    private int cadence;

    private long elapsedTime;

    private double speed;
}
