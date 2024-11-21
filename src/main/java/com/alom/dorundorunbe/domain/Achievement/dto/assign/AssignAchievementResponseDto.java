package com.alom.dorundorunbe.domain.Achievement.dto.assign;

import com.alom.dorundorunbe.domain.Achievement.domain.UserAchievement;

import java.time.LocalDateTime;

public record AssignAchievementResponseDto(
        Long userId,
        Long achievementId,
        String achievementName,
        boolean rewardClaimed,
        LocalDateTime createdAt
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
