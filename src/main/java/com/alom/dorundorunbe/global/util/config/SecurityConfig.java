package com.alom.dorundorunbe.global.util.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf->csrf.disable()) // CSRF 보호 비활성화
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(
                "/v3/api-docs/**",
                "/swagger-ui/**",
                "/swagger-ui.html",
                "/swagger-resources/**",
                "/webjars/**",
                "/actuator/**",
                "/doodle/**"
            ).permitAll() // Swagger 및 관련 리소스 허용
            .anyRequest().authenticated() // 나머지 요청은 인증 필요
        );

    http.cors(cors -> cors.disable()); // 테스트를 위해 CORS 비활성화


      return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder(){
      return new BCryptPasswordEncoder();
  }
}