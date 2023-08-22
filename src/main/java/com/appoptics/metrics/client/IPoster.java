package com.appoptics.metrics.client;

import java.net.http.HttpResponse;
import java.util.Map;

/**
 * For users which need control of actually making an HTTP submission
 */
public interface IPoster {
    HttpResponse<byte[]> post(String uri, Map<String, String> headers, byte[] payload);
}
