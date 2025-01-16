package com.alom.dorundorunbe.domain.achievement.dto.reward;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "업적 보상 수령 요청 DTO")
public record RewardAchievementRequestDto(
        @Schema(description = "유저 ID", example = "1") Long userId,
        @Schema(description = "업적 ID", example = "100") Long achievementId
) {
    public static RewardAchievementRequestDto of(Long userId, Long achievementId) {
        return new RewardAchievementRequestDto(userId, achievementId);
    }
}
