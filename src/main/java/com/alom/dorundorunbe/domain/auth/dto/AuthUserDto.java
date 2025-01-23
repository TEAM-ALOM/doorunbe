package com.alom.dorundorunbe.domain.auth.dto;

import com.alom.dorundorunbe.domain.user.domain.OAuth2Provider;

public record AuthUserDto(
        String email,
        OAuth2Provider provider
) {
    public static AuthUserDto of (String email, OAuth2Provider provider) {
        return new AuthUserDto(email, provider);
    }
}
