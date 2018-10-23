package com.appoptics.metrics.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Collections;
import java.util.List;

public class AppopticsResponse {
    @JsonProperty("errors")
    List<Object> errors = Collections.emptyList();

    public boolean isFailed() {
        return !errors.isEmpty();
    }
}
