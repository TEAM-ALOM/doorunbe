package com.alom.dorundorunbe.domain.Achievement.exception;

public class RewardAlreadyClaimedException extends RuntimeException {
    public RewardAlreadyClaimedException(String message) {
        super(message);
    }
}
