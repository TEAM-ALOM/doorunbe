package com.alom.dorundorunbe.domain.achievement.dto.query;

import com.alom.dorundorunbe.domain.achievement.domain.Achievement;
import com.alom.dorundorunbe.domain.achievement.domain.RewardType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
@Schema(description = "업적 조회 DTO")
public record AchievementDto(
        @Schema(description = "업적 ID", example = "100") Long id,
        @Schema(description = "업적 이름", example = "총 100km 보상") String name,
        @Schema(description = "보상 유형", example = "DISTANCE") RewardType rewardType,
        @Schema(description = "보상 금액", example = "1000") Long cashReward,
        @Schema(description = "배경 이미지 (TIER 보상일 경우)", example = "gold") String backgroundReward,
        @Schema(description = "업적 생성일", example = "2025-01-01T00:00:00") LocalDateTime createdAt,
        @Schema(description = "업적 수정일", example = "2025-01-02T00:00:00") LocalDateTime modifiedAt
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
