package com.alom.dorundorunbe.global.enums;

import lombok.Getter;

@Getter
public enum Tier {
    STARTER("스타터", 0.8),
    BEGINNER("비기너", 0.5),
    AMATEUR("아마추어", 0.2),
    PRO("프로", 0.1);

    private final String rank;
    private final double lpMultiplier; // LP 배율

    Tier(String rank, double lpMultiplier) {
        this.rank = rank;
        this.lpMultiplier = lpMultiplier;
    }
}
