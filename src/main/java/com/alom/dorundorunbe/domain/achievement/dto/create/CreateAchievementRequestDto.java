package com.alom.dorundorunbe.domain.achievement.dto.create;

import com.alom.dorundorunbe.domain.achievement.domain.RewardType;
import com.alom.dorundorunbe.global.enums.Tier;

public record CreateAchievementRequestDto(
        String name,
        RewardType rewardType,
        Integer distance,
        Integer cadence,
        Integer week,
        Long cash,                   // 보상 금액
        Tier tier,                   // Tier 업적일 때 사용
        String background

) {
    public static CreateAchievementRequestDto of(
            String name,
            RewardType rewardType,
            Integer distance,
            Integer cadence,
            Integer week,
            Long cash,
            Tier tier,
            String background
    )
    {
        return new CreateAchievementRequestDto(name, rewardType, distance, cadence, week, cash, tier, background);
    }
}
