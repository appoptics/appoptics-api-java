package com.appoptics.metrics.client;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Authorization {
    private Authorization() {
        // utility class, do not construct
    }

    /**
     * Builds a new HTTP Authorization header for AppOptics API requests
     *
     * @param token    the API token
     * @return the Authorization header value
     */
    public static String buildAuthHeader(String token) {
        if (token == null || token.isEmpty()) {
            throw new IllegalArgumentException("Token must be specified");
        }
        var creds = (token + ":").getBytes(StandardCharsets.UTF_8);
        return "Basic " + Base64.getEncoder().encodeToString(creds);
    }

}
