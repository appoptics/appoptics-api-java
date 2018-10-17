package com.appoptics.metrics.client;

public interface IResponseConverter {
    PostResult convert(byte[] payload, HttpResponse response);
    PostResult convert(byte[] payload, Exception exception);
}
