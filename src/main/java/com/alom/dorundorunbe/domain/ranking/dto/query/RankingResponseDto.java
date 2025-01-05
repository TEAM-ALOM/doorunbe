package com.alom.dorundorunbe.domain.ranking.dto.query;

import com.alom.dorundorunbe.domain.ranking.domain.Ranking;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class RankingResponseDto {

    private Long rankingId; // 랭킹 ID
    private boolean isFinished;
    private List<ParticipantDto> participants; // 랭킹에 참가한 사용자 목록


    public RankingResponseDto(Ranking ranking){
        this.rankingId = ranking.getId();
        this.isFinished = ranking.isFinished();
        participants = ranking.getParticipants().stream().map(ParticipantDto::new)
                .collect(Collectors.toList());
    }
}
