package com.alom.dorundorunbe.domain.achievement.exception;

public class RewardAlreadyClaimedException extends RuntimeException{
    public RewardAlreadyClaimedException(String message) {
        super(message);
    }
}
