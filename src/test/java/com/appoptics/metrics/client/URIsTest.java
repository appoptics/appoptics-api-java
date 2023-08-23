package com.appoptics.metrics.client;


import org.junit.jupiter.api.Test;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class URIsTest {

    @Test
    public void testURIPath() {
        assertEquals(URIs.removePath(URI.create("https://api.appoptics.com/")),
                URI.create("https://api.appoptics.com"));
        assertEquals(URIs.removePath(URI.create("https://api.appoptics.com")),
                URI.create("https://api.appoptics.com"));
        assertEquals(URIs.removePath(URI.create("https://api.appoptics.com:443")),
                URI.create("https://api.appoptics.com:443"));
        assertEquals(URIs.removePath(URI.create("https://api.appoptics.com/v1/metrics")),
                URI.create("https://api.appoptics.com"));

    }
}
