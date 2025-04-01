package com.meetime.hubspot_integration.infrastructure.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Value("${openapi.server.local}")
    private String localUrl;

    @Value("${openapi.server.prd}")
    private String prdUrl;

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("HubSpot Integration API")
                        .version("1.0.0")
                        .description("HubSpot Integration API Documentation")
                        .contact(new Contact().name("Support").email("support@meetime.com"))
                        .license(new License().name("License name").url("http://localhost:8080"))
                        .termsOfService("Terms of Service")
                )
                .servers(List.of(
                        new Server().url(localUrl).description("Local Environment"),
                        new Server().url(prdUrl).description("Production Environment")
                ));
    }
}