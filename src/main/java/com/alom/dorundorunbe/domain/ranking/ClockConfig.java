package com.alom.dorundorunbe.domain.ranking;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class ClockConfig {

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone(); // 기본 시스템 시간대로 설정
    }
}
