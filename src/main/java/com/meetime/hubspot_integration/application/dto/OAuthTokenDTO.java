package com.meetime.hubspot_integration.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class OAuthTokenDTO {
    @JsonProperty(value = "client_id")
    private UUID clientId;

    @JsonProperty(value = "access_token")
    private String accessToken;

    @JsonProperty(value = "refresh_token")
    private String refreshToken;

    @JsonProperty(value = "expires_in")
    private Date expiresIn;

    @JsonProperty(value = "token_type")
    private String tokenType;

    @JsonProperty(value = "scope")
    private String scope;
}