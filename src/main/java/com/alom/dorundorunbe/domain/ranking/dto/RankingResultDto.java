package com.alom.dorundorunbe.domain.ranking.dto;

import com.alom.dorundorunbe.domain.ranking.domain.UserRanking;

public record RankingResultDto(
        String nickname,
        int grade,
        double lpAwarded,
        Double averageElapsedTime,
        boolean isClaimed
) {

    public static RankingResultDto of(UserRanking userRanking){
        return new RankingResultDto(
                userRanking.getUser().getNickname(),
                userRanking.getGrade(),
                userRanking.getLpAwarded(),
                userRanking.getAverageElapsedTime(),
                userRanking.isClaimed());
    }
}
