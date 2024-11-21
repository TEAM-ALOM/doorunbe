package com.alom.dorundorunbe.domain.Achievement.domain;

import com.alom.dorundorunbe.domain.user.domain.User;
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
@Table(name = "user_achievement")
public class UserAchievement extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "achievement_id", nullable = false)
    private Achievement achievement;

    private boolean rewardClaimed;//보상 지급여부

    public void markRewardAsClaimed() {
        this.rewardClaimed = true;
    }//보상 지급되었을 때 또 보상이 지급되는 것 방지
}
