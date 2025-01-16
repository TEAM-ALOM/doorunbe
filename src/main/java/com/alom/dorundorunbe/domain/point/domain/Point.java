package com.alom.dorundorunbe.domain.point.domain;

import com.alom.dorundorunbe.global.util.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "points")
public class Point extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double rankingPoint; // 랭킹 포인트
    private Double doodlePoint;  // 두들 포인트

    /**
     * 포인트 생성 메서드
     */
    public static Point create(Double rankingPoint, Double doodlePoint) {
        return Point.builder()
                .rankingPoint(rankingPoint != null ? rankingPoint : 0.0) // 참가 안하면 0 처리
                .doodlePoint(doodlePoint != null ? doodlePoint : 0.0)
                .build();
    }
}