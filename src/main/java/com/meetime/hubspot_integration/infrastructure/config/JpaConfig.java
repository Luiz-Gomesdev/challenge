package com.meetime.hubspot_integration.infrastructure.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EntityScan({
        "com.meetime.hubspot_integration.core.model",
        "com.meetime.hubspot_integration.core.model.valueobjects",
        "com.meetime.hubspot_integration.infrastructure.adapters.persistence.entities"
})
@EnableJpaRepositories(
        basePackages = "com.meetime.hubspot_integration.infrastructure.adapters.persistence",
        considerNestedRepositories = true
)
@EnableTransactionManagement
public class JpaConfig {

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer() {
        return props -> {
            props.put("hibernate.allow_embedding_of_embeddables", true);
            props.put("hibernate.use_embedded_annotation", true);
        };
    }
}
