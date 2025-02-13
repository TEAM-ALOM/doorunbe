package com.alom.dorundorunbe.global.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Tier {
    STARTER("스타터", 0.8, 1.0, 4800),
    BEGINNER("비기너", 0.5, 1.2, 3600),
    AMATEUR("아마추어", 0.2, 1.5, 1200),
    PRO("프로", 0.1, 2.0, 0);

    private final String name;
    private final double lpMultiplier;
    private final double cashMultiplier;
    private final int timeThreshold;

    Tier(String name, double lpMultiplier, double cashMultiplier, int timeThreshold) {
        this.name = name;
        this.lpMultiplier = lpMultiplier;
        this.cashMultiplier = cashMultiplier;
        this.timeThreshold = timeThreshold;
    }

    public static Tier determineTier(double averageTime) {
        return Arrays.stream(Tier.values())
                .filter(tier -> averageTime >= tier.getTimeThreshold())
                .filter(tier -> tier != PRO) // 프로 배정 방지
                .findFirst()
                .orElse(AMATEUR);
    }
}