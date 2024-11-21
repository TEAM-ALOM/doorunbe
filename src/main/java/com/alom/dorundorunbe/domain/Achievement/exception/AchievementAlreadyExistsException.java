package com.alom.dorundorunbe.domain.Achievement.exception;


public class AchievementAlreadyExistsException extends RuntimeException {
    public AchievementAlreadyExistsException(String message) {
        super(message);
    }
}