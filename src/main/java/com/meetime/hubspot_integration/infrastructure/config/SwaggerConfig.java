package com.meetime.hubspot_integration.infrastructure.config;

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

    private static final String API_TITLE = "HubSpot Integration API";
    private static final String API_VERSION = "1.0.0";
    private static final String API_DESCRIPTION = "HubSpot Integration API Documentation";
    private static final String CONTACT_NAME = "Support";
    private static final String CONTACT_EMAIL = "support@meetime.com";
    private static final String LICENSE_NAME = "License name";
    private static final String TERMS_OF_SERVICE = "Terms of Service";

    @Bean
    public OpenAPI customOpenApi(
            @Value("${openapi.server.local}") String localUrl,
            @Value("${openapi.server.prd}") String prdUrl) {

        return new OpenAPI()
                .info(buildApiInfo())
                .servers(buildServers(localUrl, prdUrl));
    }

    private Info buildApiInfo() {
        return new Info()
                .title(API_TITLE)
                .version(API_VERSION)
                .description(API_DESCRIPTION)
                .contact(new Contact().name(CONTACT_NAME).email(CONTACT_EMAIL))
                .license(new License().name(LICENSE_NAME))
                .termsOfService(TERMS_OF_SERVICE);
    }

    private List<Server> buildServers(String localUrl, String prdUrl) {
        return List.of(
                new Server().url(localUrl).description("Local Environment"),
                new Server().url(prdUrl).description("Production Environment")
        );
    }
}
