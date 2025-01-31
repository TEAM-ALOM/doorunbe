package com.alom.dorundorunbe.global.config;

import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI openAPI() {

    SecurityScheme apiKey = new SecurityScheme()
            .type(SecurityScheme.Type.HTTP)
            .in(SecurityScheme.In.HEADER)
            .name("Authorization")
            .scheme("bearer")
            .bearerFormat("JWT");

    SecurityRequirement securityRequirement = new SecurityRequirement()
            .addList("Bearer Token");

    return new OpenAPI()
            .components(new Components().addSecuritySchemes("Bearer Token", apiKey))
            .addSecurityItem(securityRequirement)
            .info(apiInfo())
            .path("/actuator/health", new PathItem().get(new Operation().operationId("healthCheck")));
  }

  private Info apiInfo() {
    return new Info()
            .title("두런두런 백엔드 API")
            .description("두런두런과 함께 비대면 러닝을 시작해보세요")
            .version("1.0.0");
  }
}