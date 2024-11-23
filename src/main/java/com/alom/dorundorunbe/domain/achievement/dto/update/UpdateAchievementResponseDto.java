package com.alom.dorundorunbe.domain.achievement.dto.update;

import com.alom.dorundorunbe.domain.achievement.domain.Achievement;
import com.alom.dorundorunbe.domain.achievement.domain.RewardType;

import java.time.LocalDateTime;

public record UpdateAchievementResponseDto(
        Long id,
        String name,
        RewardType rewardType,
        Long cash,
        String background,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt

) {
    public static UpdateAchievementResponseDto of(Achievement achievement) {
        return new UpdateAchievementResponseDto(
                achievement.getId(),
                achievement.getName(),
                achievement.getRewardType(),
                achievement.getCash(),
                achievement.getBackground(),
                achievement.getCreatedAt(),
                achievement.getModifiedAt()
        );
    }
}
