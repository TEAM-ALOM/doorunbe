package com.alom.dorundorunbe.domain.user.domain;

import com.alom.dorundorunbe.domain.ranking.domain.Ranking;
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

    // 임의로 추가한 필드 -> kakao 로그인 구현 시 수정
    @Column(name = "kakao_id", nullable = false, unique = true)
    private String kakaoId;

    @Column(nullable = false, unique = true, length = 32)
    private String nickname;

    @Column(nullable = false, length = 32)
    private String name;

    @Column(nullable = false, length = 50)
    private String email;

    private int age;

    private String password;

    @OneToOne(mappedBy = "user") //ranking 객체를 참조하는데, 이때 ranking 내의 user가 관계 주인
    private Ranking ranking;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(nullable = false)
    private Long cash;

    public void updateCash(Long cash) {
        this.cash = cash;
    }
}
