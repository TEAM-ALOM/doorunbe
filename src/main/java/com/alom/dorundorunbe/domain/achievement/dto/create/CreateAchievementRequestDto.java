package com.alom.dorundorunbe.domain.achievement.dto.create;

import com.alom.dorundorunbe.domain.achievement.domain.RewardType;

public record CreateAchievementRequestDto(
        String name,
        RewardType rewardType,
        Integer distance,
        Integer cadence,
        Integer week,
        String background

) {
    public static CreateAchievementRequestDto of(
            String name,
            RewardType rewardType,
            Integer distance,
            Integer cadence,
            Integer week,
            String background
    )
    {
        return new CreateAchievementRequestDto(name, rewardType, distance, cadence, week, background);
    }
}
