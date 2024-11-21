package com.alom.dorundorunbe.domain.Achievement.dto.create;

import com.alom.dorundorunbe.domain.Achievement.domain.RewardType;

public record CreateAchievementRequestDto(
        String name, // 업적 이름
        RewardType rewardType, // 보상 유형
        String rewardValue, // 보상 값
        String condition // 업적 조건
) {
    public static CreateAchievementRequestDto of(String name, RewardType rewardType, String rewardValue, String condition) {
        return new CreateAchievementRequestDto(name, rewardType, rewardValue, condition);
    }
}
