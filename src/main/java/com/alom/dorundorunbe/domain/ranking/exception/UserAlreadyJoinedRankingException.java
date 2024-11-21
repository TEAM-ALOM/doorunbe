package com.alom.dorundorunbe.domain.ranking.exception;

public class UserAlreadyJoinedRankingException extends RuntimeException{
    public UserAlreadyJoinedRankingException() {
        super();
    }

    public UserAlreadyJoinedRankingException(String message) {
        super(message);
    }

    public UserAlreadyJoinedRankingException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserAlreadyJoinedRankingException(Throwable cause) {
        super(cause);
    }

    protected UserAlreadyJoinedRankingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
