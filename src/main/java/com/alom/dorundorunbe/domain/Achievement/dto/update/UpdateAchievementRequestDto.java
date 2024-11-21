package com.alom.dorundorunbe.domain.Achievement.dto.update;

public record UpdateAchievementRequestDto(
        String name,        // 수정할 업적 이름 (Optional)
        String rewardValue  // 수정할 보상 값 (Optional)

) {
    public static UpdateAchievementRequestDto of(String name, String rewardValue) {
        return new UpdateAchievementRequestDto(name, rewardValue);
    }
}
