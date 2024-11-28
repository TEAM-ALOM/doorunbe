package com.alom.dorundorunbe.domain.ranking.service;


import com.alom.dorundorunbe.domain.RunningRecord.domain.RunningRecord;
import com.alom.dorundorunbe.domain.RunningRecord.repository.RunningRecordRepository;
import com.alom.dorundorunbe.domain.ranking.domain.Ranking;
import com.alom.dorundorunbe.domain.ranking.domain.UserRanking;
import com.alom.dorundorunbe.domain.ranking.dto.claim.ClaimRankingResponseDto;
import com.alom.dorundorunbe.domain.ranking.repository.RankingRepository;
import com.alom.dorundorunbe.domain.ranking.repository.UserRankingRepository;
import com.alom.dorundorunbe.domain.user.domain.User;
import com.alom.dorundorunbe.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class RankingService {

    private final RankingRepository rankingRepository;
    private final UserRankingRepository userRankingRepository;
    private final UserRepository userRepository;
    private final RunningRecordRepository runningRecordRepository;


    @Transactional
    @Scheduled(cron = "0 0 0 ? * MON") // 매주 월요일 00:00:00에 실행
    public void finalizeRankings() {
        List<Ranking> activeRankings = rankingRepository.findAllActiveRankings();
        activeRankings.forEach(this::finalizeRanking);
    }
    private void finalizeRanking(Ranking ranking) {

        // 보상 지급 로직에서 결과 반환
        distributeRewards(ranking);

        // 랭킹 종료 처리
        ranking.finish();

        ranking.removeAllParticipants();

        // 랭킹 삭제
        rankingRepository.delete(ranking);
    }
    private void distributeRewards(Ranking ranking) {
        List<User> participants = ranking.getParticipants();

        // 상위 3개의 기록 평균 계산
        Map<User, Double> userAverageTimes = participants.stream()
                .collect(Collectors.toMap(
                        user -> user,
                        user->calculateTop3AverageElapsedTime(user,getStartOfRanking(),getEndOfRanking())
                ));

        // 기록이 3개 미만인 사용자 필터링
        List<User> sortedUsers = userAverageTimes.entrySet().stream()
                .filter(entry -> entry.getValue() != Double.MAX_VALUE)
                .sorted(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .toList();




        makeResult(ranking, sortedUsers, userAverageTimes);
    }
    private Double calculateTop3AverageElapsedTime(User user, LocalDateTime startTime, LocalDateTime endTime) {

        List<RunningRecord> records = runningRecordRepository.findTop3ByUserAndDateRangeOrderByElapsedTimeAsc(
                user, startTime, endTime
        );
        if (records.size() < 3) {
            return Double.MAX_VALUE;
        }
        return records.stream().mapToLong(RunningRecord::getElapsedTime).average().orElse(Double.MAX_VALUE);
    }

    private void makeResult(Ranking ranking, List<User> sortedUsers, Map<User, Double> userAverageTimes) {
        List<UserRanking> results = new ArrayList<>();
        int[] baseLp = {50, 30, 10};
        for (int i = 0; i < sortedUsers.size(); i++) {
            User user = sortedUsers.get(i);
            double averageElapsedTime = userAverageTimes.getOrDefault(user, Double.MAX_VALUE);
            double lpAwarded = (i < baseLp.length ? baseLp[i] : 0) * user.getTier().getLpMultiplier();

            results.add(UserRanking.builder()
                    .ranking(ranking)
                    .user(user)
                    .grade(i + 1)
                    .averageElapsedTime(averageElapsedTime == Double.MAX_VALUE ? null : averageElapsedTime)
                    .lpAwarded(lpAwarded)
                    .isClaimed(false) // 수령 여부 초기값
                    .build());
        }

        userRankingRepository.saveAll(results);
    }
    private LocalDateTime getStartOfRanking() {
        LocalDateTime now = LocalDateTime.now();
        return now.with(java.time.DayOfWeek.MONDAY)
                .withHour(17).withMinute(0).withSecond(0).withNano(0);
    }

    private LocalDateTime getEndOfRanking() {
        return getStartOfRanking()
                .plusWeeks(1)
                .withHour(0).withMinute(0).withSecond(0).withNano(0);
    }

    @Transactional
    public ClaimRankingResponseDto claimReward(Long userId, Long rankingId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        Ranking ranking = rankingRepository.findById(rankingId)
                .orElseThrow(() -> new IllegalArgumentException("랭킹 정보를 찾을 수 없습니다."));

        UserRanking result = userRankingRepository.findByUserAndRanking(user, ranking)
                .orElseThrow(() -> new IllegalArgumentException("보상 정보를 찾을 수 없습니다."));

        if (result.isClaimed()) {
            throw new IllegalStateException("이미 보상을 수령했습니다.");
        }

        // LP 지급
        user.addLp(result.getLpAwarded());
        result.markClaimed(); // 보상 수령 상태 업데이트
        UserRanking userRanking = userRankingRepository.save(result);

        return ClaimRankingResponseDto.of(userRanking);
    }



}
