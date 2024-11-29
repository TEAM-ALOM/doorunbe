package com.alom.dorundorunbe.domain.ranking.dto.delete;

import java.time.LocalDateTime;

public record DeleteRankingResponseDto(
        Long id,
        String userName,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
    public static DeleteRankingResponseDto of (
            Long id, String userName, LocalDateTime createdAt, LocalDateTime
    modifiedAt){
        return new DeleteRankingResponseDto(id, userName, createdAt, modifiedAt);
    }

}