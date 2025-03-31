package com.meetime.hubspot_integration.infrastructure.utils;

import java.util.concurrent.atomic.AtomicReference;

public final class OAuth2TokenHolder {
    private static final AtomicReference<String> TOKEN_HOLDER = new AtomicReference<>();

    private OAuth2TokenHolder() {
        throw new UnsupportedOperationException("Utility class, cannot be instantiated");
    }

    public static void setToken(String token) {
        TOKEN_HOLDER.set(token);
    }

    public static String getToken() {
        return TOKEN_HOLDER.get();
    }

    public static void clear() {
        TOKEN_HOLDER.set(null);
    }
}
