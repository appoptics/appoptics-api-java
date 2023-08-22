package com.appoptics.metrics.client;

import java.net.http.HttpResponse;

public interface IResponseConverter {
    PostResult convert(byte[] payload, HttpResponse<byte[]> response);
    PostResult convert(byte[] payload, Exception exception);
}
