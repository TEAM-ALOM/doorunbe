package com.alom.dorundorunbe.domain.ranking.dto;

import java.time.LocalDateTime;

public record JoiningRankingResponseDto(
        Long userId,
        LocalDateTime createdAt
) {

    public static JoiningRankingResponseDto of(Long userId, LocalDateTime createdAt){
        return new JoiningRankingResponseDto(userId,createdAt);
    }
}
