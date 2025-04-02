package com.meetime.hubspot_integration.infrastructure.adapters.persistence.repository;

import com.meetime.hubspot_integration.core.model.Webhook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WebhookRepository extends JpaRepository<Webhook, String> {
    Optional<Webhook> findLatestByClientId(String clientId) throws Exception;
}
