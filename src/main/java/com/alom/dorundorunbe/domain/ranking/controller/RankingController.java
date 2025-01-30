package com.alom.dorundorunbe.domain.ranking.controller;

import com.alom.dorundorunbe.domain.ranking.dto.*;

import com.alom.dorundorunbe.domain.ranking.service.RankingService;
import com.alom.dorundorunbe.domain.ranking.service.UserRankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ranking")
@RequiredArgsConstructor
public class RankingController implements RankingControllerDocs{
    private final RankingService rankingService;
    private final UserRankingService userRankingService;


    /**
     * 모든 Ranking 방 조회
     */
    @GetMapping
    public ResponseEntity<List<RankingResponseDto>> fetchAllRankings() {
        List<RankingResponseDto> rankings = rankingService.findAllRankings();
        return ResponseEntity.ok(rankings);
    }

    /**
     * 특정 Ranking 방 조회
     */
    @GetMapping("/{rankingId}")
    public ResponseEntity<RankingResponseDto> fetchRanking(@PathVariable Long rankingId) {
        RankingResponseDto ranking = rankingService.findRanking(rankingId);
        return ResponseEntity.ok(ranking);
    }





}
