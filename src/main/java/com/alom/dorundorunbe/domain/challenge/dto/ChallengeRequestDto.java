package com.alom.dorundorunbe.domain.challenge.dto;

import java.time.LocalDate;

public record ChallengeRequestDto(
    String name,
    String description,
    String challengeUrl,
    LocalDate startDate,
    LocalDate endDate,
    String location,
    int distance
) {
}
