package com.appoptics.metrics.client;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class AppopticsClientTest {
    String measuresUrl = "https://api.appoptics.com/v1/measurements";
    Duration connectTimeout = new Duration(5, TimeUnit.SECONDS);
    Duration timeout = new Duration(10, TimeUnit.SECONDS);
    Map<String, String> headers = new HashMap<String, String>();
    FakePoster poster = new FakePoster();
    String token = "abcd1234";
    AppopticsClient client = AppopticsClient.builder(token)
            .setPoster(poster)
            .setAgentIdentifier("test-lib")
            .setBatchSize(2)
            .build();

    @Before
    public void setUp() throws Exception {
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", Authorization.buildAuthHeader(token));
        headers.put("User-Agent", "test-lib appoptics-java/0.0.10");
    }

    @Test
    public void testTrimsTagName() {
        long now = System.currentTimeMillis() / 1000;
        client.postMeasures(new Measures(Collections.<Tag>emptyList(), now)
                .add(new TaggedMeasure("metric-name", 42,
                        new Tag("tagNametagNametagNametagNametagNametagNametagNametagNametagNameta", // 65 ch
                                "tagValue"))));
        assertThat(poster.posts).isEqualTo(asList(
                new Post(measuresUrl, connectTimeout, timeout, headers, new Payload()
                        .setTime(now)
                        .addMeasurement("metric-name", 42,
                                new Tag("tagNametagNametagNametagNametagNametagNametagNametagNametagNamet", // 64 ch
                                        "tagValue")))));
    }

    @Test
    public void testTrimsTagValue() throws Exception {
        long now = System.currentTimeMillis() / 1000;
        client.postMeasures(new Measures(Collections.<Tag>emptyList(), now)
                .add(new TaggedMeasure("metric-name", 42,
                        new Tag("tagName",
                                "tagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValue")))); // 256
        assertThat(poster.posts).isEqualTo(asList(
                new Post(measuresUrl, connectTimeout, timeout, headers, new Payload()
                        .setTime(now)
                        .addMeasurement("metric-name", 42,
                                new Tag("tagName",
                                        "tagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValu"))))); // 255
    }

    @Test
    public void testSendsPeriod() throws Exception {
        long now = System.currentTimeMillis() / 1000;
        client.postMeasures(new Measures(Collections.<Tag>emptyList(), now, 30)
                .add(new TaggedMeasure("foo", 42,
                        new Tag("tagName", "tagValue"))
                        .setPeriod(30)));
        assertThat(poster.posts).isEqualTo(asList(
                new Post(measuresUrl, connectTimeout, timeout, headers, new Payload()
                        .setTime(now)
                        .setPeriod(30)
                        .addMeasurement("foo", 30,42,
                                new Tag("tagName", "tagValue"))
                )));
    }

    @Test
    public void testSplitsPayloads() throws Exception {
        client.postMeasures(new Measures()
                .add(new TaggedMeasure("foo", 42,
                        new Tag("tagName", "tagValue")))
                .add(new TaggedMeasure("bar", 43,
                        new Tag("tagName", "tagValue")))
                .add(new TaggedMeasure("split", 45,
                        new Tag("tagName", "tagValue"))));

        assertThat(poster.posts).isEqualTo(asList(
                new Post(measuresUrl, connectTimeout, timeout, headers, new Payload()
                        .addMeasurement("foo", 42,
                                new Tag("tagName", "tagValue"))
                        .addMeasurement("bar", 43,
                                new Tag("tagName", "tagValue"))),
                new Post(measuresUrl, connectTimeout, timeout, headers, new Payload()
                        .addMeasurement("split", 45,
                                new Tag("tagName", "tagValue")))));
    }

    @Test
    public void testPostsATaggedMeasure() throws Exception {
        client.postMeasures(new Measures()
                .add(new TaggedMeasure("foo", 42, new Tag("x", "y"))));
        assertThat(poster.posts).isEqualTo(asList(
                new Post(measuresUrl, connectTimeout, timeout, headers, new Payload()
                        .addMeasurement("foo", 42, new Tag("x", "y")))));

    }

    @Test
    public void testPostsComplexGauge() throws Exception {
        client.postMeasures(new Measures()
                .add(new TaggedMeasure("foo", 100, 10, 20, 40, new Tag("x", "y"))));
        assertThat(poster.posts).isEqualTo(asList(
                new Post(measuresUrl, connectTimeout, timeout, headers, new Payload()
                        .addMeasurement("foo", null, 100, 10, 20, 40, new Tag("x", "y")))));
    }

    @Test
    public void testNothingPosts() throws Exception {
        FakePoster poster = new FakePoster();
        AppopticsClient client = AppopticsClient.builder("token")
                .setPoster(poster)
                .build();
        PostMeasuresResult result = client.postMeasures(new Measures());
        assertThat(result.results).isEmpty();
        assertThat(poster.posts).isEmpty();
    }

    @Test
    public void testVerifiesToken() throws Exception {
        ensureIllegalArgument(new Runnable() {
            @Override
            public void run() {
                AppopticsClient.builder(null).build();
            }
        });
        AppopticsClient.builder("token").build();
    }

    private void ensureIllegalArgument(Runnable runnable) {
        try {
            runnable.run();
            Assertions.fail("Should have failed");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }
}
