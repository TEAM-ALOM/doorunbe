package com.alom.dorundorunbe.domain.achievement.dto.update;

import com.alom.dorundorunbe.domain.achievement.domain.Achievement;
import com.alom.dorundorunbe.domain.achievement.domain.RewardType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
@Schema(description = "업적 수정 응답 DTO")
public record UpdateAchievementResponseDto(
        @Schema(description = "업적 ID", example = "100") Long id,
        @Schema(description = "업적 이름", example = "총 100km 보상") String name,
        @Schema(description = "보상 유형", example = "DISTANCE") RewardType rewardType,
        @Schema(description = "보상 금액", example = "1000") Long cash,
        @Schema(description = "배경 이미지", example = "gold") String background,
        @Schema(description = "업적 생성일", example = "2025-01-01T00:00:00") LocalDateTime createdAt,
        @Schema(description = "업적 수정일", example = "2025-01-02T00:00:00") LocalDateTime modifiedAt

) {
    public static UpdateAchievementResponseDto of(Achievement achievement) {
        return new UpdateAchievementResponseDto(
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
