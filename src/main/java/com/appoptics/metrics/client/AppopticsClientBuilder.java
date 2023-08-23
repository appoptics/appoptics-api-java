package com.appoptics.metrics.client;

import java.net.URI;
import java.time.Duration;

public class AppopticsClientBuilder {
    private final AppopticsClientAttributes attrs = new AppopticsClientAttributes();

    public AppopticsClientBuilder(String token) {
        attrs.token = token;
    }

    public AppopticsClientBuilder setURI(String uri) {
        this.attrs.uri = URI.create(uri);
        return this;
    }

    public AppopticsClientBuilder setBatchSize(int batchSize) {
        this.attrs.batchSize = batchSize;
        return this;
    }

    public AppopticsClientBuilder setConnectTimeout(Duration timeout) {
        this.attrs.connectTimeout = timeout;
        return this;
    }

    public AppopticsClientBuilder setReadTimeout(Duration timeout) {
        this.attrs.readTimeout = timeout;
        return this;
    }

    public AppopticsClientBuilder setPoster(DefaultPoster poster) {
        this.attrs.poster = poster;
        return this;
    }

    public AppopticsClientBuilder setAgentIdentifier(String identifier) {
        this.attrs.agentIdentifier = identifier;
        return this;
    }

    public AppopticsClient build() {
        return new AppopticsClient(attrs);
    }
}
