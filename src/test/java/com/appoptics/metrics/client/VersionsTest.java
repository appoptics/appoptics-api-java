package com.appoptics.metrics.client;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VersionsTest {

    @Test
    public void testFindsTheVersion() {
        final String version = Versions.getVersion("com/appoptics/metrics/valid.pom.properties", Versions.class);
        assertEquals("0.0.10", version);
    }

    @Test
    public void testDoesNotFindThePath() {
        final String version = Versions.getVersion("com/appoptics/metrics/does-not-exist", Versions.class);
        assertEquals("unknown", version);
    }

    @Test
    public void testDoesNotFindTheVersion() {
        final String version = Versions.getVersion("com/appoptics/metrics/invalid.pom.properties", Versions.class);
        assertEquals("unknown", version);
    }

}
