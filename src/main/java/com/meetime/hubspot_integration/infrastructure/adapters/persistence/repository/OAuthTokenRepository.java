package com.meetime.hubspot_integration.infrastructure.adapters.persistence.repository;

import com.meetime.hubspot_integration.core.model.OAuthToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OAuthTokenRepository extends JpaRepository<OAuthToken, UUID> {
    Optional<OAuthToken> findTopByClientIdOrderByExpiresInDesc(UUID clientId);
}
