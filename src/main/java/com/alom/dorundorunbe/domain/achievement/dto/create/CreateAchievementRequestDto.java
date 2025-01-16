package com.alom.dorundorunbe.domain.achievement.dto.create;

import com.alom.dorundorunbe.domain.achievement.domain.RewardType;
import com.alom.dorundorunbe.global.enums.Tier;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "업적 생성 요청 DTO")
public record CreateAchievementRequestDto(
        @Schema(description = "업적 이름", example = "총 100km 보상") String name,
        @Schema(description = "보상 유형", example = "DISTANCE") RewardType rewardType,
        @Schema(description = "거리 (DISTANCE 보상일 경우)", example = "100") Integer distance,
        @Schema(description = "케이던스 (CADENCE 보상일 경우)", example = "180") Integer cadence,
        @Schema(description = "주간 목표 (WEEK 보상일 경우)", example = "5") Integer week,
        @Schema(description = "보상 금액", example = "1000") Long cash,
        @Schema(description = "업적 대상 티어 (TIER 보상일 경우). 허용 값: STARTER, BEGINNER, AMATEUR, PRO", example = "PRO") Tier tier,
        @Schema(description = "티어에 맞는 테두리 (소문자로 저장됨)", example = "gold") String background

) {
    public static CreateAchievementRequestDto of(
            String name,
            RewardType rewardType,
            Integer distance,
            Integer cadence,
            Integer week,
            Long cash,
            Tier tier,
            String background
    )
    {
        return new CreateAchievementRequestDto(name, rewardType, distance, cadence, week, cash, tier, background);
    }
}
