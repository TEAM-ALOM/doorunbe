package com.alom.dorundorunbe.global.util.config;

import com.alom.dorundorunbe.domain.auth.handler.OAuthFailureHandler;
import com.alom.dorundorunbe.domain.auth.handler.OAuthSuccessHandler;
import com.alom.dorundorunbe.domain.auth.service.PrincipalUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@AllArgsConstructor
public class SecurityConfig {

  private final PrincipalUserDetailsService principalUserDetailsService;
  private final OAuthSuccessHandler oAuthSuccessHandler;
  private final OAuthFailureHandler oAuthFailureHandler;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(AbstractHttpConfigurer::disable) // CSRF 보호 비활성화
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(
                "/v3/api-docs/**",
                "/swagger-ui/**",
                "/swagger-ui.html",
                "/swagger-resources/**",
                "/webjars/**",
                "/actuator/**"
            ).permitAll() // Swagger 및 관련 리소스 허용
            .anyRequest().authenticated()) // 나머지 요청은 인증 필요
        .oauth2Login((oauth2) -> oauth2
                .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig
                        .userService(principalUserDetailsService))
                .successHandler(oAuthSuccessHandler)
                .failureHandler(oAuthFailureHandler)); // oauth2

    return http.build();
  }
}