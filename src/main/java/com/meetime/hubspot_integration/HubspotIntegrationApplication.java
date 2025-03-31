package com.meetime.hubspot_integration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.meetime.hubspot_integration")
public class HubspotIntegrationApplication {

    public static void main(String[] args) {
        SpringApplication.run(HubspotIntegrationApplication.class, args);
    }
}
