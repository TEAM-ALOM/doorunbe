package com.alom.dorundorunbe.domain.achievement.dto.query;

import com.alom.dorundorunbe.domain.achievement.domain.Achievement;
import com.alom.dorundorunbe.domain.achievement.domain.RewardType;

import java.time.LocalDateTime;

public record AchievementDto(
        Long id,
        String name,
        RewardType rewardType, // RewardType 그대로 반환
        Long cashReward,       // DISTANCE, CADENCE, WEEK의 경우 보상 금액
        String backgroundReward, // TIER의 경우 배경 정보
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
    public static AchievementDto of(Achievement achievement) {
        return new AchievementDto(
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
