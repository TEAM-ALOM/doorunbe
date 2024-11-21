package com.alom.dorundorunbe.domain.Achievement.dto.update;

import com.alom.dorundorunbe.domain.Achievement.domain.Achievement;
import com.alom.dorundorunbe.domain.Achievement.domain.RewardType;

import java.time.LocalDateTime;

public record UpdateAchievementResponseDto(
        Long id,
        String name,
        RewardType rewardType,
        String rewardValue,
        String condition,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
    public static UpdateAchievementResponseDto of(Achievement achievement) {
        return new UpdateAchievementResponseDto(
                achievement.getId(),
                achievement.getName(),
                achievement.getRewardType(),
                achievement.getRewardValue(),
                achievement.getCondition(),
                achievement.getCreatedAt(),
                achievement.getModifiedAt()
        );
    }
}
