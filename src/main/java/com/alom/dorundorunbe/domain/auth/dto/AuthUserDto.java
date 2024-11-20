package com.alom.dorundorunbe.domain.auth.dto;

import lombok.Getter;

@Getter
public record AuthUserDto(
        String nickname,
        String email
) {
    public static AuthUserDto of (String nickname, String email) {
        return new AuthUserDto(nickname, email);
    }
}
