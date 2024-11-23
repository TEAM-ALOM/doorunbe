package com.alom.dorundorunbe.domain.ranking.dto;

import com.alom.dorundorunbe.global.enums.Tier;
import com.alom.dorundorunbe.domain.ranking.domain.Ranking;

import java.time.LocalDateTime;

public record UpdateRankingResponseDto(
        Long userId,
        Tier tier,
        long distance,
        long time,
        int cadence,

        int grade,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
    public static UpdateRankingResponseDto of(Ranking ranking) {
        return new UpdateRankingResponseDto(
                ranking.getUser().getId(),
                ranking.getTier(),
                ranking.getDistance(),
                ranking.getTime(),
                ranking.getCadence(),
                ranking.getGrade(),
                ranking.getCreatedAt(),
                ranking.getModifiedAt()
        );
    }
}
