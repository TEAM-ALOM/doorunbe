package com.alom.dorundorunbe.domain.user.domain;

import com.alom.dorundorunbe.domain.ranking.domain.Ranking;
import com.alom.dorundorunbe.global.enums.Tier;
import com.alom.dorundorunbe.global.util.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;
import lombok.Builder;

@Entity @Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true, length = 32)
    private String nickname;

    @Column(nullable = false, length = 32)
    private String name;

    private int age;

    private String password;

    @OneToOne(mappedBy = "user") //ranking 객체를 참조하는데, 이때 ranking 내의 user가 관계 주인
    private Ranking ranking;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(nullable = false)
    private Long cash;

    @Enumerated(EnumType.STRING)
    private Tier tier; // BACKGROUND 업적일 때 사용 (유저의 티어와 매칭)

    @Column(length = 64)
    private String background; // BACKGROUND 업적일 때 보상 배경

    public void updateCash(Long cash) {
        this.cash = cash;
    }

    public void updateBackground(String newBackground) {
        this.background = newBackground;
    }

    public void addCash(Long rewardValue){
        cash += rewardValue;
    }
}
