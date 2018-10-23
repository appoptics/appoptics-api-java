package com.appoptics.metrics.client;

import org.junit.Assert;
import org.junit.Test;

public class AuthorizationTest {
    @Test(expected = IllegalArgumentException.class)
    public void testAuthHeaderRejectsEmptyToken() throws Exception {
        Authorization.buildAuthHeader("");
    }

    @Test
    public void testProducesTheCorrectHeader() throws Exception {
        final String header = Authorization.buildAuthHeader("1234ABCD");
        Assert.assertEquals("Basic MTIzNEFCQ0Q6", header);
    }
}
