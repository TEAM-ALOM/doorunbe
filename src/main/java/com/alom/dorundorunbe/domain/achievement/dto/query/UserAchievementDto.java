package com.alom.dorundorunbe.domain.achievement.dto.query;

import com.alom.dorundorunbe.domain.achievement.domain.RewardType;
import com.alom.dorundorunbe.domain.achievement.domain.UserAchievement;

import java.time.LocalDateTime;

public record UserAchievementDto(
        Long id,
        String name,
        RewardType rewardType,
        Long cashReward,
        String backgroundReward,
        boolean rewardClaimed,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
    public static UserAchievementDto of(UserAchievement userAchievement) {
        return new UserAchievementDto(
                userAchievement.getAchievement().getId(),
                userAchievement.getAchievement().getName(),
                userAchievement.getAchievement().getRewardType(),
                userAchievement.getAchievement().getCash(),
                userAchievement.getAchievement().getBackground(),
                userAchievement.isRewardClaimed(),
                userAchievement.getAchievement().getCreatedAt(),
                userAchievement.getAchievement().getModifiedAt()
        );
    }
}
