package com.appoptics.metrics.client;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.net.http.HttpResponse;

@ToString
@EqualsAndHashCode
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

}
