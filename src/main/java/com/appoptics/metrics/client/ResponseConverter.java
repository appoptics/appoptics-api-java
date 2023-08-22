package com.appoptics.metrics.client;

import java.net.http.HttpResponse;

public class ResponseConverter implements IResponseConverter {
    @Override
    public PostResult convert(byte[] payload, HttpResponse<byte[]> response) {
        return new PostResult(true, payload, response);
    }

    @Override
    public PostResult convert(byte[] payload, Exception exception) {
        return new PostResult(true, payload, exception);
    }
}
