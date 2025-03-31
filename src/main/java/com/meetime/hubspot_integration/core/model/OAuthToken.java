package com.meetime.hubspot_integration.core.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "oauth_tokens")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OAuthToken {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID clientId;

    @Column(name = "access_token", nullable = false)
    private String accessToken;

    @Column(name = "refresh_token", nullable = false)
    private String refreshToken;

    @Column(name = "expires_in", nullable = false)
    private Date expiresIn;

    @Column(name = "token_type", nullable = false)
    private String tokenType;

    @Column(name = "scope")
    private String scope;

    public boolean isExpired() {
        return new Date().after(this.expiresIn);
    }
}
