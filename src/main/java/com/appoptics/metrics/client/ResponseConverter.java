package com.appoptics.metrics.client;

public class ResponseConverter implements IResponseConverter {
    @Override
    public PostResult convert(byte[] payload, HttpResponse response) {
        return new PostResult(true, payload, response);
    }

    @Override
    public PostResult convert(byte[] payload, Exception exception) {
        return new PostResult(true, payload, exception);
    }
}
