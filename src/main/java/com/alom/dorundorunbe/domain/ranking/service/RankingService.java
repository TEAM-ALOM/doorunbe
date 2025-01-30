package com.alom.dorundorunbe.domain.ranking.service;


import com.alom.dorundorunbe.domain.ranking.domain.Ranking;
import com.alom.dorundorunbe.domain.ranking.dto.RankingResponseDto;
import com.alom.dorundorunbe.domain.ranking.repository.RankingRepository;
import com.alom.dorundorunbe.domain.ranking.repository.UserRankingRepository;
import com.alom.dorundorunbe.domain.runningrecord.domain.RunningRecord;
import com.alom.dorundorunbe.domain.runningrecord.repository.RunningRecordRepository;
import com.alom.dorundorunbe.domain.user.domain.User;
import com.alom.dorundorunbe.domain.user.repository.UserRepository;
import com.alom.dorundorunbe.global.enums.Tier;
import com.alom.dorundorunbe.global.exception.BusinessException;
import com.alom.dorundorunbe.global.exception.ErrorCode;
import com.alom.dorundorunbe.global.util.point.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class RankingService {
    private final RankingRepository rankingRepository;
    private final UserRankingRepository userRankingRepository;
    private final UserRepository userRepository;
    private final UserRankingService userRankingService;
    private final RunningRecordRepository runningRecordRepository;
    private final PointService pointService;


    @Transactional(readOnly = true)
    public List<RankingResponseDto> findAllRankings() {
        List<Ranking> rankings = rankingRepository.findAll();
        return rankings.stream().map(RankingResponseDto::new).toList();
    }

    @Transactional(readOnly = true)
    public RankingResponseDto findRanking(Long rankingId){
        Optional<Ranking> ranking = rankingRepository.findById(rankingId);
        return ranking.map(RankingResponseDto::new)
                .orElseThrow(() -> new BusinessException(ErrorCode.RANKING_NOT_FOUND));


    }


    public void handleRankingParticipation(Long userId, Long rankingId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

        Ranking ranking = rankingRepository.findById(rankingId).orElseThrow(() -> new BusinessException(ErrorCode.RANKING_NOT_FOUND));

        if (user.getTier() == null) {
            if (user.getRankingParticipationDate() == null) {
                user.startRankingParticipation();
            }
            participateFirstThreeRuns(user);
        } else {
            validateUserParticipation(user, ranking);

        }
        user.setParticipateRanking();
        userRankingService.createUserRanking(user, ranking);


    }

    public void participateFirstThreeRuns(User user) {
        double averageTime = getAverageTimeFromRunningRecord(user);
        Tier assignedTier = Tier.determineTier(averageTime);
        user.setTier(assignedTier);

    }
    private void validateUserParticipation(User user, Ranking ranking) {
        if (!user.getTier().equals(ranking.getTier())) {
            throw new BusinessException(ErrorCode.RANKING_TIER_MISMATCH);
        }
        if (user.isRankingParticipated()) {
            throw new BusinessException(ErrorCode.RANKING_ALREADY_PARTICIPATED);
        }
    }

    private double getAverageTimeFromRunningRecord(User user) {
        // 랭킹 참가 이후 5km 기록 중 상위 3개 가져오기
        List<RunningRecord> records = runningRecordRepository
                .findByUserAndDistanceAndCreatedAtAfter(user, 5.0, user.getRankingParticipationDate());

        if (records.size() < 3) {
            throw new BusinessException(ErrorCode.RANKING_MINIMUM_RECORDS_NOT_MET);
        }


        //티어 결정
        List<Integer> top3Times = records.stream()
                .map(RunningRecord::getElapsedTime)
                .limit(3)
                .toList();

        return top3Times.stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElseThrow(() -> new BusinessException(ErrorCode.FAIL_PROCEED));
    }

    //랭킹 보상 지급(일주일 단위로 지급되어야하고(스케줄러 이용) 사용자에게 lp와 cash 지급하고 deleteRankingRecords 호출
    @Scheduled(cron = "0 0 0 * * MON") // 매주 월요일 00:00 실행
    public void distributeWeeklyRewardsAndClearRankings() {


        List<Ranking> rankings = rankingRepository.findAll();

        for (Ranking ranking : rankings) {


            pointService.giveRankingRewardToUsersByRanking(ranking.getId());



            // 랭킹 데이터 삭제
            deleteRankingRecords(ranking.getId());
        }
    }

    //랭킹 기록 모두 삭제
    public void deleteRankingRecords(Long rankingId) {
        if (!rankingRepository.existsById(rankingId)) {
            throw new BusinessException(ErrorCode.RANKING_NOT_FOUND);
        }

        // 벌크 삭제 쿼리 실행
        userRankingRepository.deleteByRankingId(rankingId);



    }

}






