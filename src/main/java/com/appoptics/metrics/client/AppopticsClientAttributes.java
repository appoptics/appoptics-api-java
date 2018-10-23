package com.appoptics.metrics.client;

import java.net.URI;
import java.util.concurrent.TimeUnit;

public class AppopticsClientAttributes {
    public URI uri = URI.create("https://api.appoptics.com");
    public int batchSize = 500;
    public Duration connectTimeout = new Duration(5, TimeUnit.SECONDS);
    public Duration readTimeout = new Duration(10, TimeUnit.SECONDS);
    public String token;
    public IPoster poster = new DefaultPoster();
    public int maxInflightRequests = 10;
    public String agentIdentifier = "unknown";

}
