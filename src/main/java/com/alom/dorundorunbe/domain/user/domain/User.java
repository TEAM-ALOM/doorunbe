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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ranking_id")
    @JsonIgnore
    private Ranking ranking; // 현재 참가 중인 랭킹

    @Column(nullable = false)
    private Long cash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Tier tier; // BACKGROUND 업적일 때 사용 (유저의 티어와 매칭)

    @Column(nullable = false, length = 64)
    private String background; // BACKGROUND 업적일 때 보상 배경

    @Column(nullable = false)
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