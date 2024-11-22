package com.alom.dorundorunbe.domain.ranking.controller;

import com.alom.dorundorunbe.domain.ranking.domain.Ranking;
import com.alom.dorundorunbe.domain.ranking.dto.*;
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

    @PostMapping("/join")
    public ResponseEntity<JoiningRankingResponseDto> joinRanking(@RequestBody JoiningRankingRequestDto requestDto){
        Long rankId = rankingService.joinRanking(requestDto.userId());
        Ranking joinRank = rankingService.findOne(rankId);
        JoiningRankingResponseDto responseDto = JoiningRankingResponseDto.of(joinRank.getUser().getId(), joinRank.getCreatedAt());
        return ResponseEntity.ok(responseDto);
    }


    @PutMapping
    public ResponseEntity<UpdateRankingResponseDto>updateRanking(@RequestBody UpdateRankingRequestDto requestDto){
        Long rankId = rankingService.updateRanking(
                requestDto.userId(),
                requestDto.tier(),
                requestDto.distance(),
                requestDto.time(),
                requestDto.cadence(),
                requestDto.grade()
        );
        Ranking updateRank = rankingService.findOne(rankId);


        UpdateRankingResponseDto responseDto = UpdateRankingResponseDto.of(updateRank);
        return ResponseEntity.ok(responseDto);

    }


    @GetMapping
    public ResponseEntity<List<RankingDto>> fetchRankings() {
        List<Ranking> rankings = rankingService.fetchAllRankings();
//        if (rankings.isEmpty()) {
//            throw new RankingNotFoundException("랭킹 데이터가 없습니다.");
//        }
        //빈 리스트로 반환이 되었을 때 이를 오류처리할것인가에 대한 처리
        List<RankingDto> list = rankings.stream()
                .map(RankingDto::of)
                .toList();
        return ResponseEntity.ok(list);
    }


}
