package com.alom.dorundorunbe.domain.achievement.dto.update;

import com.alom.dorundorunbe.domain.achievement.domain.RewardType;

public record UpdateAchievementRequestDto(
        String name,
        RewardType rewardType,
        Long cash, // DISTANCE, CADENCE, WEEK일 경우 보상 금액 변경
        String background

) {
    public static UpdateAchievementRequestDto of(String name, RewardType rewardType, Long cash, String background) {
        return new UpdateAchievementRequestDto(name, rewardType,cash,background);
    }
}
