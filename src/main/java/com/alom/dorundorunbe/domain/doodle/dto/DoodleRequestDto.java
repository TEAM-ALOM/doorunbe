package com.alom.dorundorunbe.domain.doodle.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoodleRequestDto {
    @NotBlank(message = "이름을 입력해 주세요.")
    private String name;

    @NotNull(message = "주간 러닝 목표 거리를 입력해 주세요.")
    @Positive(message = "유효하지 않은 값입니다.")
    private double weeklyGoalDistance;

    @NotNull(message = "주간 러닝 목표 횟수를 입력해 주세요.")
    @Positive(message = "유효하지 않은 값입니다.")
    private int weeklyGoalCount;

    @NotNull(message = "목표 케이던시를 입력해 주세요.")
    @Positive(message = "유효하지 않은 값입니다.")
    private double weeklyGoalCadence;

    @NotNull(message = "목표 페이스를 입력해 주세요.")
    private double weeklyGoalPace;

    @NotNull(message = "목표 참여 인원 수를 입력해 주세요.")
    @Positive(message = "참여 인원 수는 0보다 커야 합니다.")
    private int goalParticipationCount;

    @NotBlank(message = "비밀번호를 입력해 주세요.")
    private String password;

    @NotNull(message = "최대 인원 수를 입력해주세요.")
    @Positive(message = "유효하지 않은 값입니다.")
    private int maxParticipant;

    @NotNull(message = "러닝 모드를 선택해주세요.(걷기 또는 달리기)")
    private boolean isRunning;

    @NotNull(message = "목표 심박존을 입력해주세요.")
    @Min(value = 1, message = "심박존은 최소 1이어야 합니다.")
    @Max(value = 5, message = "심박존은 최대 5이어야 합니다.")
    private int weeklyGoalHeartRateZone;

    private Long userId;

}
