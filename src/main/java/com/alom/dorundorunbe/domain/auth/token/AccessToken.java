package com.alom.dorundorunbe.domain.auth.token;

public record AccessToken(
        String token
) {
    public static AccessToken of(String token) {
        return new AccessToken(token);
    }
}
