package com.alom.dorundorunbe.domain.runningrecord.dto;

import com.alom.dorundorunbe.domain.item.dto.EquippedItemResponseDto;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RunningRecordResponseDto {
    private Long id;

    private String date;

    private String startTime;

    private String endTime;

    private Double distance;

    private Integer cadence;

    private Integer elapsedTime;

    private Double speed;

    private Boolean isFinished;

    private List<EquippedItemResponseDto> items;
}
