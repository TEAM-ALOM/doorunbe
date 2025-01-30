package com.alom.dorundorunbe.global.util.point.reward;

import com.alom.dorundorunbe.global.enums.Tier;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum RankingReward {
    FIRST_PLACE(1, 1000, 30),
    SECOND_PLACE(2, 500, 20),
    THIRD_PLACE(3, 300, 10),
    FOURTH_PLACE(4, 100, 5),
    OTHER(0, 0, 0);

    private final long grade;
    private final long baseCash;
    private final double baseLp;

    RankingReward(long grade, long baseCash, double baseLp) {
        this.grade = grade;
        this.baseCash = baseCash;
        this.baseLp = baseLp;
    }

    public static RankingReward getRewardByGrade(long grade) {
        return Arrays.stream(values())
                .filter(reward -> reward.grade == grade)
                .findFirst()
                .orElse(OTHER);
    }

    public long calculateCash(Tier tier) {
        return Math.round(baseCash * tier.getCashMultiplier());
    }

    public double calculateLp(Tier tier) {
        return baseLp * tier.getLpMultiplier();
    }
}

