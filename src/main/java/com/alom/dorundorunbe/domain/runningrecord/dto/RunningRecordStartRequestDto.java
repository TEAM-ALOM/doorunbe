package com.alom.dorundorunbe.domain.runningrecord.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RunningRecordStartRequestDto {
    private Long userId;

    private String date;

    private String startTime;
}
