package com.alom.dorundorunbe.domain.ranking.domain;

import com.alom.dorundorunbe.domain.user.domain.User;
import com.alom.dorundorunbe.global.enums.Tier;
import com.alom.dorundorunbe.global.util.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "ranking", cascade = CascadeType.ALL)
    @BatchSize(size = 10)
    @Builder.Default
    private List<User> participants = new ArrayList<>(); // 랭킹 참가자 목록



    @Column(nullable = false)
    private boolean isFinished;

    public void finish(){
        isFinished =true;
    }

    public void addParticipant(User user) {
        if (!participants.contains(user)) { // 중복 방지
            participants.add(user);
            user.joinRanking(this); // 양방향 관계 설정
        }
    }

    public void removeAllParticipants() {

        new ArrayList<>(this.participants).forEach(this::removeParticipant);
    }

    public void removeParticipant(User user) {
        if (participants.contains(user)) {
            participants.remove(user); // 랭킹에서 사용자 제거
            user.leaveRanking(); // 사용자의 랭킹 관계 제거
        }
    }



}
