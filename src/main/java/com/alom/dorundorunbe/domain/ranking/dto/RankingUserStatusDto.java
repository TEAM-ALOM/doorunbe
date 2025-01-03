package com.alom.dorundorunbe.domain.ranking.dto;

import java.time.LocalDateTime;

public record RankingUserStatusDto(
        String userName,
        LocalDateTime rankingStartTime,
        int runningCount,          // 랭킹 시작부터 뛴 횟수
        double averageElapsedTime, // 랭킹 시작부터 평균 시간
        int grade) {
    public static RankingUserStatusDto of(String userName,
                                          LocalDateTime rankingStartTime,
                                          int runningCount,          // 랭킹 시작부터 뛴 횟수
                                          double averageElapsedTime, // 랭킹 시작부터 평균 시간
                                          int grade){
        return new RankingUserStatusDto(userName,rankingStartTime,runningCount,averageElapsedTime,grade);

    }



}
