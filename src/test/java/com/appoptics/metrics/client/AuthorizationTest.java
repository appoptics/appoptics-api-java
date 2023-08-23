package com.appoptics.metrics.client;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AuthorizationTest {
    @Test
    public void testAuthHeaderRejectsEmptyToken() {
        assertThrows(IllegalArgumentException.class, () -> {
            Authorization.buildAuthHeader("");
        });
    }

    @Test
    public void testProducesTheCorrectHeader() throws Exception {
        final String header = Authorization.buildAuthHeader("1234ABCD");
        assertEquals("Basic MTIzNEFCQ0Q6", header);
    }
}
