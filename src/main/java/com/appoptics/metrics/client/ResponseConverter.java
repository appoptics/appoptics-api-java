package com.appoptics.metrics.client;

import java.net.http.HttpResponse;

public class ResponseConverter {
    public PostResult convert(byte[] payload, HttpResponse<byte[]> response) {
        return new PostResult(true, payload, response);
    }

    public PostResult convert(byte[] payload, Exception exception) {
        return new PostResult(true, payload, exception);
    }
}
