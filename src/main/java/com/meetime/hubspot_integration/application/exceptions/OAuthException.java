package com.meetime.hubspot_integration.application.exceptions;

import lombok.Getter;

@Getter
public class OAuthException extends RuntimeException {
    private final String errorCode;
    private final String errorDescription;

    public OAuthException(String errorCode, String errorDescription) {
        super("OAuth error [%s]: %s".formatted(errorCode, errorDescription));
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
    }

    public static OAuthException invalidToken(String details) {
        return new OAuthException("invalid_token", "token: " + details);
    }
}
