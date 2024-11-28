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

    // 소셜 로그인 서비스의 사용자 id (사용자 인증 시 필요)
    private String socialId;

    // 소셜 로그인 시 받아올 정보 -> 두런 내에서 사용자 식별에 사용
    @Column(nullable = false, unique = true)
    private String email;

    // 앱 내에서 사용할 닉네임 (ㅇㅇ두)
    @Column(nullable = false, unique = true, length = 32)
    private String nickname;

    // 필요 없는 필드 같음
    @Column(nullable = false, length = 32)
    private String name;

    // 없애야 하는 필드!!
    private String password;

    @OneToOne(mappedBy = "user") //ranking 객체를 참조하는데, 이때 ranking 내의 user가 관계 주인
    private Ranking ranking;

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
