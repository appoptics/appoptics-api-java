package com.appoptics.metrics.client;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.Charset;

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
        if (token == null || "".equals(token)) {
            throw new IllegalArgumentException("Token must be specified");
        }
        return String.format("Basic %s", base64Encode((token + ":").getBytes(Charset.forName("UTF-8"))));
    }

    private static String base64Encode(byte[] bytes) {
        return DatatypeConverter.printBase64Binary(bytes);
    }
}
