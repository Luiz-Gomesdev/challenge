package com.meetime.hubspot_integration.infrastructure.adapters.persistence.repository;

import com.meetime.hubspot_integration.core.model.WebhookEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WebhookRepository extends JpaRepository<WebhookEvent, String> {
    Optional<WebhookEvent> findLatestByClientId(String clientId) throws Exception;
}
