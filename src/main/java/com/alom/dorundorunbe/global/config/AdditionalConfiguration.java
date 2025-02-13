package com.alom.dorundorunbe.global.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({
        /*KakaoCredentials.class,
        JwtCredentials.class,*/
        AwsS3Credentials.class
})
public class AdditionalConfiguration {
}