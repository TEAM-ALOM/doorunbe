package com.alom.dorundorunbe.domain.achievement.exception;

public class UserAchievementAlreadyClaimedException extends RuntimeException{
    public UserAchievementAlreadyClaimedException(String message) {
        super(message);
    }
}
