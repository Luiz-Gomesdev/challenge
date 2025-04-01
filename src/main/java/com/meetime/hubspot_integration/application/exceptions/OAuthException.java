package com.meetime.hubspot_integration.application.exceptions;

import lombok.Getter;

@Getter
public class OAuthException extends RuntimeException {
    private static final String OAUTH_ERROR_MESSAGE = "OAuth error [%s]: %s";

    private final String errorCode;
    private final String errorDescription;

    public OAuthException(String errorCode, String errorDescription) {
        super(String.format(OAUTH_ERROR_MESSAGE, errorCode, errorDescription));
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
    }
}