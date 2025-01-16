package com.alom.dorundorunbe.domain.achievement.dto.assign;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "업적 할당 요청 DTO")
public record AssignAchievementRequestDto(
        @Schema(description = "user ID", example = "1") Long userId,
        @Schema(description = "achievement ID", example = "100") Long achievementId

) {
    public static AssignAchievementRequestDto of(Long userId, Long achievementId) {
        return new AssignAchievementRequestDto(userId, achievementId);
    }
}
