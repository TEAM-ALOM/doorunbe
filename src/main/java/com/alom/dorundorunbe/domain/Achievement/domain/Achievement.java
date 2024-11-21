package com.alom.dorundorunbe.domain.Achievement.domain;

import com.alom.dorundorunbe.global.util.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 상속관계를 만들어서 controller단에서 /distance, /cadence, /week, /rank(비기너,프로 등등)를 각각 받는 controller를 만들지 아니면 그냥
 *     condition으로받아서 필요정보를 처리할지 고민
 *     확장성이 그렇게까지 있을까 싶어서 일단 condition으로 작성(item처럼 확장성이 있어보이진 않아서..)
 *     "totalDistance>=50"
 *     "averageCadence=170~180"
 *     "weeklyRuns>=3"
 *     "rank=BEGINNER" 이 형태로 받으면 처리 가능(숫자, rank 이런것들은 형태만 지키면 확장, 수정 다 가능)
 */

@Entity @Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "achievement")
public class Achievement extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "achievement_id")
    private Long id;

    @Column(nullable = false, length = 64)
    private String name;

    @Enumerated(EnumType.STRING)
    private RewardType rewardType;// COIN, BACKGROUND

    @Column(nullable = false)
    private String rewardValue; // 보상 값 (숫자나 Background 이름)

    @Column(name = "achievement_condition", nullable = false)
    private String condition;

    public static Achievement of(Achievement achievement) {
        return Achievement.builder()
                .id(achievement.getId())
                .name(achievement.getName())
                .rewardType(achievement.getRewardType())
                .rewardValue(achievement.getRewardValue())
                .build();
    }
    public void updateRewardValue(String rewardValue) {
        this.rewardValue = rewardValue;


    }
    public void updateName(String name) {
        this.name = name;
    }




}
