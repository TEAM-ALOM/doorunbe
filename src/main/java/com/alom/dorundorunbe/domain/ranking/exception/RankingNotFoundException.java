package com.alom.dorundorunbe.domain.ranking.exception;

public class RankingNotFoundException extends RuntimeException{
    public RankingNotFoundException() {
        super();
    }

    public RankingNotFoundException(String message) {
        super(message);
    }

    public RankingNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public RankingNotFoundException(Throwable cause) {
        super(cause);
    }

    protected RankingNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
