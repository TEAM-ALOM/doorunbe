package com.alom.dorundorunbe.domain.ranking.service;


import com.alom.dorundorunbe.domain.ranking.domain.Ranking;
import com.alom.dorundorunbe.domain.ranking.dto.RankingResponseDto;
import com.alom.dorundorunbe.domain.ranking.repository.RankingRepository;
import com.alom.dorundorunbe.domain.ranking.repository.UserRankingRepository;
import com.alom.dorundorunbe.domain.runningrecord.repository.RunningRecordRepository;
import com.alom.dorundorunbe.domain.user.repository.UserRepository;
import com.alom.dorundorunbe.global.util.point.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
}






