package com.appoptics.metrics.client;

import org.junit.Test;

import java.net.URI;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class URIsTest {

    @Test
    public void testURIPath() throws Exception {
        assertThat(URIs.removePath(URI.create("https://api.appoptics.com/")),
                equalTo(URI.create("https://api.appoptics.com")));
        assertThat(URIs.removePath(URI.create("https://api.appoptics.com")),
                equalTo(URI.create("https://api.appoptics.com")));
        assertThat(URIs.removePath(URI.create("https://api.appoptics.com:443")),
                equalTo(URI.create("https://api.appoptics.com:443")));
        assertThat(URIs.removePath(URI.create("https://api.appoptics.com/v1/metrics")),
                equalTo(URI.create("https://api.appoptics.com")));

    }
}
