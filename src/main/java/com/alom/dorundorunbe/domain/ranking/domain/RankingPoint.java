package com.alom.dorundorunbe.domain.ranking.domain;

import com.alom.dorundorunbe.global.util.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "ranking_point")
public class RankingPoint extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private double point;

    public static RankingPoint of(double point){
        return RankingPoint.builder().
                point(point).build();
    }
}