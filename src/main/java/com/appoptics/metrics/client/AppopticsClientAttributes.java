package com.appoptics.metrics.client;

import java.net.URI;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class AppopticsClientAttributes {
    public URI uri = URI.create("https://api.appoptics.com");
    public int batchSize = 500;
    public Duration connectTimeout = Duration.of(5, ChronoUnit.SECONDS);
    public Duration readTimeout = Duration.of(10, ChronoUnit.SECONDS);
    public String token;
    public IPoster poster = new DefaultPoster(connectTimeout, readTimeout);
    public int maxInflightRequests = 10;
    public String agentIdentifier = "unknown";

}
