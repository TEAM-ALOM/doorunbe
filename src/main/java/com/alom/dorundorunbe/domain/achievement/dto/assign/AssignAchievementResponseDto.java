package com.alom.dorundorunbe.domain.achievement.dto.assign;

import com.alom.dorundorunbe.domain.achievement.domain.UserAchievement;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
@Schema(description = "업적 할당 응답 DTO")
public record AssignAchievementResponseDto(
        @Schema(description = "사용자 ID", example = "1") Long userId,
        @Schema(description = "업적 ID", example = "100") Long achievementId,
        @Schema(description = "업적 이름", example = "총 100km 보상") String achievementName,
        @Schema(description = "보상 수령 여부", example = "false") boolean rewardClaimed,
        @Schema(description = "업적 할당 날짜", example = "2025-01-01T00:00:00") LocalDateTime createdAt
) {
    public static AssignAchievementResponseDto of(UserAchievement userAchievement) {
        return new AssignAchievementResponseDto(
                userAchievement.getUser().getId(),
                userAchievement.getAchievement().getId(),
                userAchievement.getAchievement().getName(),
                userAchievement.isRewardClaimed(),
                userAchievement.getCreatedAt()
        );
    }
}
