package com.alom.dorundorunbe.domain.achievement.dto.reward;

import com.alom.dorundorunbe.domain.achievement.domain.UserAchievement;

import java.time.LocalDateTime;

public record RewardAchievementResponseDto(
        Long userId,
        Long achievementId,
        String achievementName,
        String rewardType,
        String rewardValue,
        boolean rewardClaimed,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {

    public static RewardAchievementResponseDto of(UserAchievement userAchievement) {
        return new RewardAchievementResponseDto(
                userAchievement.getUser().getId(),
                userAchievement.getAchievement().getId(),
                userAchievement.getAchievement().getName(),
                userAchievement.getAchievement().getRewardType().toString(),
                userAchievement.getAchievement().getRewardValue(),
                userAchievement.isRewardClaimed(),
                userAchievement.getCreatedAt(),
                userAchievement.getModifiedAt()
        );
    }
}
