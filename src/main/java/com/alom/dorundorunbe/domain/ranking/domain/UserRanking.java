package com.alom.dorundorunbe.domain.ranking.domain;

import com.alom.dorundorunbe.domain.user.domain.User;
import com.alom.dorundorunbe.global.util.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_ranking")
public class UserRanking extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ranking_id", nullable = false)
    private Ranking ranking;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private int grade; // 사용자 순위

    @Column
    private Double averageElapsedTime; // 사용자 기록 평균 (충족 못하면 null)

    @Column(nullable = false)
    private double lpAwarded; // 지급될 LP

    @Column(nullable = false)
    private boolean isClaimed; // 보상 수령 여부

    public void markClaimed() {
        this.isClaimed = true;
    }
}
