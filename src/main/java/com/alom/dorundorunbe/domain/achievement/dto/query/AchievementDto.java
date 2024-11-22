package com.alom.dorundorunbe.domain.achievement.dto.query;

import com.alom.dorundorunbe.domain.achievement.domain.Achievement;

import java.time.LocalDateTime;

public record AchievementDto(
        Long id,
        String name,
        String rewardType,
        String rewardValue,

        LocalDateTime createdAt
) {
    public static AchievementDto of(Achievement achievement) {
        return new AchievementDto(
                achievement.getId(),
                achievement.getName(),
                achievement.getRewardType().toString(),
                achievement.getRewardValue(),
                achievement.getCreatedAt()
        );
    }
}