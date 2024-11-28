package com.alom.dorundorunbe.domain.user.domain;

import com.alom.dorundorunbe.domain.ranking.domain.Ranking;
import com.alom.dorundorunbe.global.enums.Tier;
import com.alom.dorundorunbe.global.util.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ranking_id")
    @JsonIgnore
    private Ranking ranking; // 현재 참가 중인 랭킹

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(nullable = false)
    private Long cash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private Tier tier; // BACKGROUND 업적일 때 사용 (유저의 티어와 매칭)

    @Column(nullable = true, length = 64)
    private String background; // BACKGROUND 업적일 때 보상 배경

    @Column(nullable = true)
    private double lp;

    public void updateCash(Long cash) {
        this.cash = cash;
    }

    public void updateBackground(String newBackground) {
        this.background = newBackground;
    }

    public void addCash(Long rewardValue){
        cash += rewardValue;
    }

    public void addLp(double lpPoints) {
        this.lp += lpPoints;
    }

    public void joinRanking(Ranking ranking){
        if (this.ranking != ranking) { // 중복 설정 방지
            this.ranking = ranking;
            ranking.addParticipant(this); // 양방향 관계 설정
        }
    }

    public void leaveRanking(){
        if (this.ranking != null) {
            this.ranking.getParticipants().remove(this); // 랭킹의 참가자 목록에서 제거
            this.ranking = null; // 사용자와 랭킹 관계 해제
        }
    }
}
