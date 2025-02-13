package com.alom.dorundorunbe.domain.ranking.dto.create;

import java.time.LocalDateTime;

public record CreateRankingResponseDto(
        Long id,
        String nickname,
        LocalDateTime createdAt
) {
    public static CreateRankingResponseDto of(Long id, String nickname, LocalDateTime createdAt) {
        return new CreateRankingResponseDto(id, nickname, createdAt);
    }

}