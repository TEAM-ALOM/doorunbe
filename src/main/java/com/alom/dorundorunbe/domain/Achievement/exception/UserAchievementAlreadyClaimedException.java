package com.alom.dorundorunbe.domain.Achievement.exception;

public class UserAchievementAlreadyClaimedException extends RuntimeException {
    public UserAchievementAlreadyClaimedException(String message) {
        super(message);
    }
}
