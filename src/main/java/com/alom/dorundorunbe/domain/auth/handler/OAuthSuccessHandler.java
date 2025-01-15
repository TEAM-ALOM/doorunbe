package com.alom.dorundorunbe.domain.auth.handler;

import com.alom.dorundorunbe.domain.auth.dto.PrincipalUserDetails;
import com.alom.dorundorunbe.domain.auth.provider.JwtTokenProvider;
import com.alom.dorundorunbe.domain.auth.token.AccessToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OAuthSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) {
        // 유저 정보 추출 -> jwt access 토큰 생성 -> access 토큰을 헤더에 저장

        PrincipalUserDetails userDetails = (PrincipalUserDetails) authentication.getPrincipal();

        AccessToken accessToken = jwtTokenProvider.generateAccessToken(userDetails.getUsername());

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.OK.value());
        response.setHeader("Authorization", "Bearer " + accessToken.token());
    }
}
