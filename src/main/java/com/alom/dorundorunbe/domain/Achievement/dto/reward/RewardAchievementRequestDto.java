package com.alom.dorundorunbe.domain.Achievement.dto.reward;

public record RewardAchievementRequestDto(
        Long userId,
        Long achievementId
) {
    public static RewardAchievementRequestDto of(Long userId, Long achievementId) {
        return new RewardAchievementRequestDto(userId, achievementId);
    }
}
