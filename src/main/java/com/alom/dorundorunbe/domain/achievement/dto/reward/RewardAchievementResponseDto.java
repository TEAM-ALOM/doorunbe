package com.alom.dorundorunbe.domain.achievement.dto.reward;

import com.alom.dorundorunbe.domain.achievement.domain.RewardType;
import com.alom.dorundorunbe.domain.achievement.domain.UserAchievement;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
@Schema(description = "보상 수령 응답 DTO")
public record RewardAchievementResponseDto(
        @Schema(description = "사용자 ID", example = "1") Long userId,
        @Schema(description = "업적 ID", example = "100") Long achievementId,
        @Schema(description = "업적 이름", example = "총 100km 보상") String achievementName,
        @Schema(description = "보상 유형", example = "DISTANCE") RewardType rewardType,
        @Schema(description = "보상 금액", example = "1000") Long cashReward,
        @Schema(description = "배경 이미지 (TIER 보상일 경우)", example = "gold") String backgroundReward,
        @Schema(description = "보상 수령 여부", example = "true") boolean rewardClaimed,
        @Schema(description = "업적 생성일", example = "2025-01-01T00:00:00") LocalDateTime createdAt,
        @Schema(description = "업적 수정일", example = "2025-01-02T00:00:00") LocalDateTime modifiedAt
) {
    public static RewardAchievementResponseDto of(UserAchievement userAchievement){
        return new RewardAchievementResponseDto(
                userAchievement.getUser().getId(),
                userAchievement.getAchievement().getId(),
                userAchievement.getAchievement().getName(),
                userAchievement.getAchievement().getRewardType(),
                userAchievement.getAchievement().getCash(),
                userAchievement.getAchievement().getBackground(),
                userAchievement.isRewardClaimed(),
                userAchievement.getCreatedAt(),
                userAchievement.getModifiedAt()
        );
    }
}
