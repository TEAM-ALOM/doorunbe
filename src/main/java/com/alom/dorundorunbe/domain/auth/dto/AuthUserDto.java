package com.alom.dorundorunbe.domain.auth.dto;

public record AuthUserDto(
        String socialId,
        String email
) {
    public static AuthUserDto of (String socialId, String email) {
        return new AuthUserDto(socialId, email);
    }
}
