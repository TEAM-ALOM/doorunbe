package com.alom.dorundorunbe.domain.ranking.controller;

import com.alom.dorundorunbe.domain.ranking.dto.RankingResponseDto;
import com.alom.dorundorunbe.domain.ranking.dto.UserRankingDto;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

public interface RankingControllerDocs {

    @Operation(
            summary = "모든 Ranking 방 조회",
            description = "모든 Ranking 방 정보를 반환합니다."
    )
    @GetMapping
    ResponseEntity<List<RankingResponseDto>> fetchAllRankings();

    @Operation(
            summary = "특정 Ranking 방 조회",
            description = "주어진 ID를 가진 Ranking 방 정보를 반환합니다."
    )
    @GetMapping("/{rankingId}")
    ResponseEntity<RankingResponseDto> fetchRanking(@PathVariable Long rankingId);

    @Operation(
            summary = "특정 Ranking에 User 추가",
            description = "주어진 User ID를 특정 Ranking 방에 추가합니다."
    )
    @PostMapping("/{rankingId}/users/{userId}")
    ResponseEntity<Void> addUserToRanking(@PathVariable Long rankingId, @PathVariable Long userId);

    @Operation(
            summary = "특정 Ranking의 User 기록 조회",
            description = "특정 User의 Ranking 기록을 조회합니다."
    )
    @GetMapping("/{rankingId}/participants/{userId}")
    ResponseEntity<UserRankingDto> getUserRanking(@PathVariable Long userId);
}