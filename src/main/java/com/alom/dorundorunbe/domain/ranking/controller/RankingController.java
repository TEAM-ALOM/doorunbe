package com.alom.dorundorunbe.domain.ranking.controller;

import com.alom.dorundorunbe.domain.ranking.domain.Ranking;
import com.alom.dorundorunbe.domain.ranking.dto.*;
import com.alom.dorundorunbe.domain.ranking.dto.create.CreateRankingResponseDto;
import com.alom.dorundorunbe.domain.ranking.dto.delete.DeleteRankingResponseDto;
import com.alom.dorundorunbe.domain.ranking.service.RankingQueueService;
import com.alom.dorundorunbe.domain.ranking.service.RankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ranking")
@RequiredArgsConstructor
public class RankingController {
    private final RankingService rankingService;
    private final RankingQueueService rankingQueueService;

    @PostMapping("/join/{id}")
    public ResponseEntity<CreateRankingResponseDto> joinQueue(@PathVariable("id") Long userId) {
        CreateRankingResponseDto response = rankingQueueService.joinQueue(userId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/cancel/{id}")
    public ResponseEntity<DeleteRankingResponseDto> cancelQueue(@PathVariable("id") Long userId) {
        DeleteRankingResponseDto response = rankingQueueService.cancelQueue(userId);
        return ResponseEntity.ok(response);
    }



}
