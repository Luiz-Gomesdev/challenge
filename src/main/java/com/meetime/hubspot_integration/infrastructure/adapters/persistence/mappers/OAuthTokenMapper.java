package com.meetime.hubspot_integration.infrastructure.adapters.persistence.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meetime.hubspot_integration.application.dto.OAuthTokenDTO;

public class OAuthTokenMapper {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static OAuthTokenDTO authConverter(String json) throws Exception {
        return objectMapper.readValue(json, OAuthTokenDTO.class);
    }

}
