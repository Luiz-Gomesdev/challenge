package com.meetime.hubspot_integration.application.exceptions;

import lombok.Getter;

@Getter
public class HubSpotIntegrationException extends RuntimeException {
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final String INTERNAL_ERROR_CODE = "INTERNAL_ERROR";

    private final int statusCode;
    private final String hubspotErrorCode;

    public HubSpotIntegrationException(String message, int statusCode, String hubspotErrorCode) {
        super(String.format("HubSpot integration failure [%d - %s]: %s",
                statusCode, hubspotErrorCode, message));
        this.statusCode = statusCode;
        this.hubspotErrorCode = hubspotErrorCode;
    }

    public HubSpotIntegrationException(String message, Throwable cause) {
        this(message, cause, INTERNAL_SERVER_ERROR, INTERNAL_ERROR_CODE);
    }

    public HubSpotIntegrationException(String message, Throwable cause, int statusCode, String hubspotErrorCode) {
        super(String.format("HubSpot integration failure [%d - %s]: %s",
                statusCode, hubspotErrorCode, message), cause);
        this.statusCode = statusCode;
        this.hubspotErrorCode = hubspotErrorCode;
    }

    public static HubSpotIntegrationException fromResponse(int statusCode, String hubspotErrorCode, String message) {
        return new HubSpotIntegrationException(message, statusCode, hubspotErrorCode);
    }
}
