package com.alom.dorundorunbe.domain.ranking.service;


import com.alom.dorundorunbe.domain.ranking.domain.Ranking;
import com.alom.dorundorunbe.domain.ranking.dto.RankingResponseDto;
import com.alom.dorundorunbe.domain.ranking.repository.RankingRepository;
import com.alom.dorundorunbe.domain.ranking.repository.UserRankingRepository;
import com.alom.dorundorunbe.domain.runningrecord.repository.RunningRecordRepository;
import com.alom.dorundorunbe.domain.user.repository.UserRepository;
import com.alom.dorundorunbe.global.exception.BusinessException;
import com.alom.dorundorunbe.global.exception.ErrorCode;
import com.alom.dorundorunbe.global.util.point.service.PointService;
import lombok.RequiredArgsConstructor;
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
}






