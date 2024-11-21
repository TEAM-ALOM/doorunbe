package com.alom.dorundorunbe.domain.Achievement.dto.assign;

public record AssignAchievementRequestDto(
        Long userId,
        Long achievementId
) {

    public static AssignAchievementRequestDto of(Long userId, Long achievementId) {
        return new AssignAchievementRequestDto(userId, achievementId);
    }
}
