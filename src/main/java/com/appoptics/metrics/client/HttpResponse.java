package com.appoptics.metrics.client;

public interface HttpResponse {
    int getResponseCode();
    byte[] getResponseBody();
}
