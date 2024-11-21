package com.alom.dorundorunbe.domain.RunningRecord.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RunningRecordStartRequestDto {
    private Long userId;

    private LocalDateTime date;

    private LocalDateTime startTime;
}
