package com.alom.dorundorunbe.domain.achievement.dto.create;

import com.alom.dorundorunbe.domain.achievement.domain.Achievement;
import com.alom.dorundorunbe.domain.achievement.domain.RewardType;

import java.time.LocalDateTime;


public record CreateAchievementResponseDto(
        Long id,
        String name,
        RewardType rewardType,
        String rewardValue,
        String condition,
        LocalDateTime createdAt
) {
    public static CreateAchievementResponseDto of(Achievement achievement) {
        return new CreateAchievementResponseDto(
                achievement.getId(),
                achievement.getName(),
                achievement.getRewardType(),
                achievement.getRewardValue(),
                achievement.getCondition(),
                achievement.getCreatedAt()
        );
    }
}
