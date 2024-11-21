package com.alom.dorundorunbe.domain.ranking.dto;

import com.alom.dorundorunbe.domain.ranking.domain.Tier;

public record UpdateRankingRequestDto(
        Long userId,
        Tier tier,
        long distance,
        long time,
        int cadence,
        int grade

) {
    public static UpdateRankingRequestDto of(Long userId, Tier tier, long distance, long time, int cadence, int grade) {
        return new UpdateRankingRequestDto(userId, tier, distance, time, cadence, grade);
    }
}
