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
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor 
public class RankingService { //랭킹 참가, 랭킹 스케줄 로직
    private final RankingRepository rankingRepository;
    private final UserRankingRepository userRankingRepository;
    private final UserRepository userRepository;
    private final UserRankingService userRankingService;
    private final RunningRecordRepository runningRecordRepository;
    private final RankingRewardService rankingRewardService;


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

    @Transactional
    public void handleRankingParticipation(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

        if (userRankingRepository.existsByUser(user)) {
            throw new BusinessException(ErrorCode.RANKING_ALREADY_PARTICIPATED);
        }

       if(user.getTier() == null){
           checkPlacementTest(user);
           return;
       }

        joinRanking(user);



    }

    /**
     * ✅ 배치고사 진행 여부 확인 및 처리
     */
    private void checkPlacementTest(User user) {

        if (user.getRankingParticipationDate() == null) {
            user.startRankingParticipation(); // 배치고사 시작
            return;
        }


        long recordCount = runningRecordRepository.countByUserAndDistanceAndCreatedAtAfter(
                user, 5.0, user.getRankingParticipationDate());


        if (recordCount < 3) {

            throw new BusinessException(ErrorCode.RANKING_MINIMUM_RECORDS_NOT_MET);

        }


        assignTierAfterPlacementTest(user);
        joinRanking(user);
    }


    private void assignTierAfterPlacementTest(User user) {
        double averageTime = getAverageTimeFromRunningRecord(user);
        Tier assignedTier = Tier.determineTier(averageTime);
        user.setTier(assignedTier);
    }

    /**
     * ✅ 랭킹 참가 처리
     */
    private void joinRanking(User user) {
        Ranking ranking = rankingRepository.findByTier(user.getTier())
                .orElseThrow(() -> new BusinessException(ErrorCode.RANKING_NOT_FOUND));

        userRankingService.createUserRanking(user, ranking);
    }





    private double getAverageTimeFromRunningRecord(User user) {
        // 랭킹 참가 이후 5km 기록 중 상위 3개 가져오기
        List<RunningRecord> records = runningRecordRepository
                .findTop3FastestRecordsAfterParticipation(user, 5.0, user.getRankingParticipationDate(), PageRequest.of(0, 3));

        if (records.size() < 3) {
            throw new BusinessException(ErrorCode.RANKING_MINIMUM_RECORDS_NOT_MET);
        }

        return records.stream()
                .mapToInt(RunningRecord::getElapsedTime)
                .average()
                .orElseThrow(() -> new BusinessException(ErrorCode.FAIL_PROCEED));
    }

    //랭킹 보상 지급(일주일 단위로 지급되어야하고(스케줄러 이용) 사용자에게 lp와 cash 지급하고 deleteRankingRecords 호출
    @Scheduled(cron = "0 0 0 * * MON") // 매주 월요일 00:00 실행
    public void distributeWeeklyRewardsAndClearRankings() {
        rankingRewardService.processWeeklyRewards();
    }
    

}






