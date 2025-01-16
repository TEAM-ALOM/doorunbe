package com.alom.dorundorunbe.domain.achievement.dto.create;

import com.alom.dorundorunbe.domain.achievement.domain.Achievement;
import com.alom.dorundorunbe.domain.achievement.domain.RewardType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
@Schema(description = "업적 생성 응답 DTO")
public record CreateAchievementResponseDto(
        @Schema(description = "업적 ID", example = "100") Long id,
        @Schema(description = "업적 이름", example = "총 100km 보상") String name,
        @Schema(description = "보상 유형", example = "DISTANCE") RewardType rewardType,
        @Schema(description = "거리 (DISTANCE 보상일 경우)", example = "100") Integer distance,
        @Schema(description = "케이던스 (CADENCE 보상일 경우)", example = "180") Integer cadence,
        @Schema(description = "주간 목표 (WEEK 보상일 경우)", example = "5") Integer week,
        @Schema(description = "배경 이미지", example = "gold") String background,
        @Schema(description = "업적 생성일", example = "2025-01-01T00:00:00") LocalDateTime createdAt

) {
    public static CreateAchievementResponseDto of(Achievement achievement){
        return new CreateAchievementResponseDto(
                achievement.getId(),
                achievement.getName(),
                achievement.getRewardType(),
                achievement.getDistance(),
                achievement.getCadence(),
                achievement.getWeek(),
                achievement.getBackground(),
                achievement.getCreatedAt()

        );
    }
}
