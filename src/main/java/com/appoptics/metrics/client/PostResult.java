package com.appoptics.metrics.client;

import java.net.http.HttpResponse;

public class PostResult {
    public final boolean md;
    public final byte[] payload;
    public final Exception exception;
    public final HttpResponse<byte[]> response;

    public PostResult(boolean md, byte[] payload, HttpResponse<byte[]> response) {
        this.md = md;
        this.payload = payload;
        this.response = response;
        this.exception = null;
    }

    public PostResult(boolean md, byte[] payload, Exception exception) {
        this.md = md;
        this.payload = payload;
        this.exception = exception;
        this.response = null;
    }

    public boolean isError() {
        if (exception != null) {
            return true;
        } else if (response == null) {
            return true;
        }
        int code = response.statusCode();
        if (!md && code == 200) {
            return false;
        }
        if (md && code / 100 == 2) {
            AppopticsResponse response = Json.deserialize(this.response.body(), AppopticsResponse.class);
            if (!response.isFailed()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        if (exception != null) {
            return exception.toString();
        }
        if (response != null) {
            int code = response.statusCode();
            byte[] body = response.body();
            return "code:" + code + " response:" + new String(body);
        }
        return "invalid post result";
    }
}
