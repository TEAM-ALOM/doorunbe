package com.alom.dorundorunbe.domain.achievement.domain;

import com.alom.dorundorunbe.global.enums.Tier;
import com.alom.dorundorunbe.global.util.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity @Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Achievement extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "achievement_id")
    private Long id;

    @Column(nullable = false, length = 32)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RewardType rewardType; // DISTANCE, CADENCE, WEEK, BACKGROUND

    @Column(nullable = true)
    private Integer distance; // DISTANCE 업적일 때 사용

    @Column(nullable = true)
    private Integer cadence; // CADENCE 업적일 때 사용

    @Column(nullable = true)
    private Integer week; // WEEK 업적일 때 사용

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private Tier tier; // BACKGROUND 업적일 때 사용 (유저의 티어와 매칭)

    @Column(nullable = true)
    private Long cash; // 보상 금액 (DISTANCE, CADENCE, WEEK 업적일 때 사용)

    @Column(nullable = true, length = 64)
    private String background; // BACKGROUND 업적일 때 보상 배경

    public void updateName(String name) {
        this.name = name;
    }

    public void updateDistance(Integer distance) {
        this.distance = distance;
    }

    public void updateCadence(Integer cadence) {
        this.cadence = cadence;
    }

    public void updateWeek(Integer week) {
        this.week = week;
    }

    public void updateTier(Tier tier) {
        this.tier = tier;
    }

    public void updateCash(Long cash) {
        this.cash = cash;
    }

    public void updateBackground(String background) {
        this.background = background;
    }
}
