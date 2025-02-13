package com.alom.dorundorunbe.global.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "cloud.aws.s3")
public class AwsS3Credentials {

    private final String bucket;            // s3 bucket 이름
    private final String imageUrl;          // 이미지 기본 url

}
