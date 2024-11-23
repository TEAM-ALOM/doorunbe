package com.alom.dorundorunbe.domain.achievement.dto.create;

import com.alom.dorundorunbe.domain.achievement.domain.Achievement;
import com.alom.dorundorunbe.domain.achievement.domain.RewardType;

import java.time.LocalDateTime;

public record CreateAchievementResponseDto(
        Long id,
        String name,
        RewardType rewardType,
        Integer distance,
        Integer cadence,
        Integer week,
        String background,
        LocalDateTime createdAt

) {
    public static CreateAchievementResponseDto of(Achievement achievement){
        return new CreateAchievementResponseDto(
                achievement.getId(),
                achievement.getName(),
                achievement.getRewardType(),
                achievement.getDistance(),
                achievement.getCadence(),
                achievement.getWeek(),
                achievement.getBackground(),
                achievement.getCreatedAt()

        );
    }
}
