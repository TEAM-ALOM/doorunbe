package com.alom.dorundorunbe.domain.doodle.dto;

import com.alom.dorundorunbe.domain.doodle.domain.Doodle;
import com.alom.dorundorunbe.global.enums.Tier;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Builder
@Getter
@Setter
public class DoodleResponseDto {
    private Long id;
    private String name;
    private Double weeklyGoalDistance;
    private int weeklyGoalCount;
    private Double weeklyGoalCadence;
    private Double weeklyGoalPace;
    private boolean isRunning;
    private int weeklyGoalHeartRateZone;
    private Integer goalParticipationCount;
    private Integer maxParticipant;
    private List<UserDoodleDto> participants;
    private boolean isPublic;
    private double doodlePoint;
    private boolean isGoalActive;
    private Tier requiredTier;

    public static DoodleResponseDto from(Doodle doodle) {
        // Doodle 엔티티의 participants 리스트를 UserDoodleDto 리스트로 변환
        List<UserDoodleDto> userDoodleDtos = Optional.ofNullable(doodle.getParticipants())
                .orElse(Collections.emptyList()) // participants가 null일 경우 빈 리스트 사용
                .stream()
                .map(UserDoodleDto::from) // 각 UserDoodle을 UserDoodleDto로 변환
                .collect(Collectors.toList());

        // DoodleResponseDto로 변환하여 반환
        return DoodleResponseDto.builder()
                .id(doodle.getId())
                .name(doodle.getName())
                .weeklyGoalDistance(doodle.getWeeklyGoalDistance())
                .weeklyGoalCount(doodle.getWeeklyGoalCount())
                .weeklyGoalCadence(doodle.getWeeklyGoalCadence())
                .weeklyGoalPace(doodle.getWeeklyGoalPace())
                .weeklyGoalHeartRateZone(doodle.getWeeklyGoalHeartRateZone())
                .isRunning(doodle.isRunning())
                .goalParticipationCount(doodle.getGoalParticipationCount())
                .maxParticipant(doodle.getMaxParticipant())
                .participants(userDoodleDtos) // 참가자 리스트 설정
                .isPublic(doodle.isPublic())
                .doodlePoint(doodle.getDoodlePoint())
                .isGoalActive(doodle.isGoalActive())
                .requiredTier(doodle.getRequiredTier())
                .build();
    }
}
