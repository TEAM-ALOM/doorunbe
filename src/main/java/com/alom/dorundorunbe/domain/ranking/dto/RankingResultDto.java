package com.alom.dorundorunbe.domain.ranking.dto;

public record RankingResultDto(
        String userName,
        int grade,
        double lpAwarded,
        Double averageElapsedTime,
        boolean isClaimed
) {

    public static RankingResultDto of(UserRanking userRanking){
        return new RankingResultDto( userRanking.getUser().getName(),
                userRanking.getGrade(),
                userRanking.getLpAwarded(),
                userRanking.getAverageElapsedTime(),
                userRanking.isClaimed());
    }
}
