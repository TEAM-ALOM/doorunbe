package com.alom.dorundorunbe.domain.achievement.dto.update;

import com.alom.dorundorunbe.domain.achievement.domain.RewardType;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "업적 업데이트 요청 DTO")
public record UpdateAchievementRequestDto(
        @Schema(description = "업적 이름", example = "새로운 목표 이름") String name,
        @Schema(description = "보상 유형", example = "DISTANCE") RewardType rewardType,
        @Schema(description = "보상 금액 (DISTANCE, CADENCE, WEEK 보상일 경우)", example = "5000") Long cash,
        @Schema(description = "티어 보상 테두리 (소문자로 저장됨)", example = "gold") String background

) {
    public static UpdateAchievementRequestDto of(String name, RewardType rewardType, Long cash, String background) {
        return new UpdateAchievementRequestDto(name, rewardType,cash,background);
    }
}
