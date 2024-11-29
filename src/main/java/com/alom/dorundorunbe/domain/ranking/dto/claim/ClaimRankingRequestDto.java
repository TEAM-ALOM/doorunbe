package com.alom.dorundorunbe.domain.ranking.dto.claim;

public record ClaimRankingRequestDto(
        Long userId,
        Long rankingId
) {
    public static  ClaimRankingRequestDto of(Long userId, Long rankingId){
        return new ClaimRankingRequestDto(userId, rankingId);
    }
}

