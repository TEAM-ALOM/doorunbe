package com.alom.dorundorunbe.domain.ranking.dto;

import com.alom.dorundorunbe.domain.ranking.domain.Ranking;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "Ranking 응답 DTO")
public class RankingResponseDto {
    @Schema(description = "Ranking 방 ID", example = "1")
    private Long rankingId;

    @Schema(description = "Tier", example = "스타터")
    private String tier;

    @Schema(description = "Ranking 참여자 목록")
    private List<UserRankingDto> participants;

    public RankingResponseDto(Ranking ranking) {
        this.rankingId = ranking.getId();
        this.tier = ranking.getTier().name();
        participants = ranking.getParticipants().stream()
                .map(UserRankingDto::of)
                .toList();
    }
}
