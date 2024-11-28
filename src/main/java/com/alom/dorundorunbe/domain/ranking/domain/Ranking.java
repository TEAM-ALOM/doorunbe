package com.alom.dorundorunbe.domain.ranking.domain;

import com.alom.dorundorunbe.domain.user.domain.User;
import com.alom.dorundorunbe.global.enums.Tier;
import com.alom.dorundorunbe.global.util.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

@Entity @Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ranking")
public class Ranking extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rank_id")
    private Long id;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "rank_level", nullable = true)
    @Enumerated(EnumType.STRING)
    private Tier tier; //user에도 rank핗드가 있어야 될 것 같습니다 user.getRank로..일단 true로 테스트
    //이름 변경->티어

    private long time;

    private long distance;

    private int cadence;

    private int grade;

    public void updateRanking(Tier tier, long distance, long time, int cadence, int grade){
        this.tier = tier;
        this.distance = distance;
        this.time = time;
        this.cadence = cadence;
        this.grade = grade;
    }

}
