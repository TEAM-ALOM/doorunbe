package com.alom.dorundorunbe.domain.challenge.domain;

import com.alom.dorundorunbe.domain.challenge.dto.ChallengeResponseDto;
import com.alom.dorundorunbe.global.util.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Challenge extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 32)
    private String name;

    private String description;

    private String challengeUrl;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    private String location;

    @Column(nullable = false)
    private int distance;

    public void update(String name, String description, String challengeUrl, LocalDate startDate, LocalDate endDate, String location, int distance) {
        this.name = name;
        this.description = description;
        this.challengeUrl = challengeUrl;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
        this.distance = distance;
    }

    public ChallengeResponseDto toResponseDto() {
        return new ChallengeResponseDto(
            this.id,
            this.name,
            this.description,
            this.challengeUrl,
            this.startDate,
            this.endDate,
            this.location,
            this.distance
        );
    }


}
