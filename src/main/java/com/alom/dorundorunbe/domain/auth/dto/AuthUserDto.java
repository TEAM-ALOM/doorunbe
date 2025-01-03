package com.alom.dorundorunbe.domain.auth.dto;

public record AuthUserDto(
        String email
) {
    public static AuthUserDto of (String email) {
        return new AuthUserDto(email);
    }
}
