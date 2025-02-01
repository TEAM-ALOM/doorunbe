package com.alom.dorundorunbe.domain.ranking.dto.claim;

import com.alom.dorundorunbe.domain.ranking.domain.UserRanking;

import java.time.LocalDateTime;

public record ClaimRankingResponseDto(
        Long userId,
        Long rankingId,
        String nickname,
        int grade,
        double lpAwarded,
        boolean rewardClaimed,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
    public static ClaimRankingResponseDto of(UserRanking userRanking){
        return new ClaimRankingResponseDto(
                userRanking.getUser().getId(),
                userRanking.getRanking().getId(),
                userRanking.getUser().getNickname(),
                userRanking.getGrade(),
                userRanking.getLpAwarded(),
                userRanking.isClaimed(),
                userRanking.getCreatedAt(),
                userRanking.getModifiedAt()
        );
    }

}
