package com.meetime.hubspot_integration.application.ports.outbound;

import com.meetime.hubspot_integration.core.model.OAuthToken;
import java.util.Optional;

public interface OAuthTokenRepositoryPort {
    Optional<OAuthToken> findLatestToken();
    OAuthToken save(OAuthToken token);
    void delete(OAuthToken token);
}
