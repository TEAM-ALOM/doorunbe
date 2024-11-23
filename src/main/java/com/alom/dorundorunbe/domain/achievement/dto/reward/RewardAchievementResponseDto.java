package com.alom.dorundorunbe.domain.achievement.dto.reward;

import com.alom.dorundorunbe.domain.achievement.domain.RewardType;
import com.alom.dorundorunbe.domain.achievement.domain.UserAchievement;

import java.time.LocalDateTime;

public record RewardAchievementResponseDto(
        Long userId,
        Long achievementId,
        String achievementName,
        RewardType rewardType,
        Long cashReward,
        String backgroundReward,
        boolean rewardClaimed,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
    public static RewardAchievementResponseDto of(UserAchievement userAchievement){
        return new RewardAchievementResponseDto(
                userAchievement.getUser().getId(),
                userAchievement.getAchievement().getId(),
                userAchievement.getAchievement().getName(),
                userAchievement.getAchievement().getRewardType(),
                userAchievement.getAchievement().getCash(),
                userAchievement.getAchievement().getBackground(),
                userAchievement.isRewardClaimed(),
                userAchievement.getCreatedAt(),
                userAchievement.getModifiedAt()
        );
    }
}
