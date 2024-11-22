package com.alom.dorundorunbe.domain.ranking.dto;

public record JoiningRankingRequestDto(
        Long userId
) {
    public static JoiningRankingRequestDto of(Long userId){
        return new JoiningRankingRequestDto(userId);
    }
}
