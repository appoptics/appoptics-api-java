package com.appoptics.metrics.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Map;

public class DefaultPoster {
    private static final Logger log = LoggerFactory.getLogger(DefaultPoster.class);
    private final HttpClient client;
    private final Duration readTimeout;

    public DefaultPoster(Duration connectTimeout, Duration readTimeout) {
        client = HttpClient.newBuilder()
                .connectTimeout(connectTimeout)
                .version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.NEVER)
                .build();
        this.readTimeout = readTimeout;
    }

    public HttpResponse<byte[]> post(String uri, Map<String, String> headers, byte[] payload) {
        try {
            var requestBuilder = HttpRequest.newBuilder()
                    .uri(new URI(uri))
                    .timeout(readTimeout)
                    .POST(HttpRequest.BodyPublishers.ofByteArray(payload));

            for (Map.Entry<String, String> hdr : headers.entrySet()) {
                requestBuilder.header(hdr.getKey(), hdr.getValue());
            }

            return client.send(requestBuilder.build(), HttpResponse.BodyHandlers.ofByteArray());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
