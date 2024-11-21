package com.alom.dorundorunbe.domain.challenge.dto;

import java.time.LocalDate;

public record ChallengeResponseDto(
    Long id,
    String name,
    String description,
    String challengeUrl,
    LocalDate startDate,
    LocalDate endDate,
    String location,
    int distance
) {}
