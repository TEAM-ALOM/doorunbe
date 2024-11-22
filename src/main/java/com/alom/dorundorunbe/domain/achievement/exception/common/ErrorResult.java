package com.alom.dorundorunbe.domain.achievement.exception.common;

public record ErrorResult(String code,
                          String message) {
    public static ErrorResult of(String code, String message) {
        return new ErrorResult(code, message);
    }

}