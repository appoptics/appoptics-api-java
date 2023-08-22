package com.appoptics.metrics.client;

import org.junit.Before;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.mock;

public class AppopticsClientTest {
    String measuresUrl = "https://api.appoptics.com/v1/measurements";
    Duration connectTimeout = Duration.of(5, ChronoUnit.SECONDS);
    Duration timeout = Duration.of(10, ChronoUnit.SECONDS);
    Map<String, String> headers = new HashMap<String, String>();
    IPoster poster = mock(IPoster.class);
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
        headers.put("User-Agent", "test-lib appoptics-api-java/0.0.10");
    }

//    @Test
//    public void testTrimsTagName() {
//        long now = System.currentTimeMillis() / 1000;
//        client.postMeasures(new Measures(Collections.<Tag>emptyList(), now)
//                .add(new Measure("metric-name", 42,
//                        new Tag("tagNametagNametagNametagNametagNametagNametagNametagNametagNameta", // 65 ch
//                                "tagValue"))));
//        assertThat(poster.posts).isEqualTo(asList(
//                new Post(measuresUrl, connectTimeout, timeout, headers, new Payload()
//                        .setTime(now)
//                        .addMeasurement("metric-name", 42,
//                                new Tag("tagNametagNametagNametagNametagNametagNametagNametagNametagNamet", // 64 ch
//                                        "tagValue")))));
//    }
//
//    @Test
//    public void testTrimsTagValue() throws Exception {
//        long now = System.currentTimeMillis() / 1000;
//        client.postMeasures(new Measures(Collections.<Tag>emptyList(), now)
//                .add(new Measure("metric-name", 42,
//                        new Tag("tagName",
//                                "tagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValue")))); // 256
//        assertThat(poster.posts).isEqualTo(asList(
//                new Post(measuresUrl, connectTimeout, timeout, headers, new Payload()
//                        .setTime(now)
//                        .addMeasurement("metric-name", 42,
//                                new Tag("tagName",
//                                        "tagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValuetagValu"))))); // 255
//    }
//
//    @Test
//    public void testSendsPeriod() throws Exception {
//        long now = System.currentTimeMillis() / 1000;
//        client.postMeasures(new Measures(Collections.<Tag>emptyList(), now, 30)
//                .add(new Measure("foo", 42,
//                        new Tag("tagName", "tagValue"))
//                        .setPeriod(30)));
//        assertThat(poster.posts).isEqualTo(asList(
//                new Post(measuresUrl, connectTimeout, timeout, headers, new Payload()
//                        .setTime(now)
//                        .setPeriod(30)
//                        .addMeasurement("foo", 30,42,
//                                new Tag("tagName", "tagValue"))
//                )));
//    }
//
//    @Test
//    public void testSendsEpoch() throws Exception {
//        long now = System.currentTimeMillis() / 1000;
//        long measureTime = now - 60;
//        client.postMeasures(new Measures(Collections.<Tag>emptyList(), now, 30)
//                .add(new Measure("foo", 42, 1, 5, 41,
//                        new Tag("tagName", "tagValue"))
//                        .setPeriod(30)
//                        .setEpoch(measureTime)));
//        assertThat(poster.posts).isEqualTo(asList(
//                new Post(measuresUrl, connectTimeout, timeout, headers, new Payload()
//                        .setTime(now)
//                        .setPeriod(30)
//                        .addMeasurement("foo", 30, measureTime, 42D, 1, 5, 41,
//                                new Tag("tagName", "tagValue"))
//
//                )));
//    }
//
//    @Test
//    public void testSplitsPayloads() throws Exception {
//        client.postMeasures(new Measures()
//                .add(new Measure("foo", 42,
//                        new Tag("tagName", "tagValue")))
//                .add(new Measure("bar", 43,
//                        new Tag("tagName", "tagValue")))
//                .add(new Measure("split", 45,
//                        new Tag("tagName", "tagValue"))));
//
//        assertThat(poster.posts).isEqualTo(asList(
//                new Post(measuresUrl, connectTimeout, timeout, headers, new Payload()
//                        .addMeasurement("foo", 42,
//                                new Tag("tagName", "tagValue"))
//                        .addMeasurement("bar", 43,
//                                new Tag("tagName", "tagValue"))),
//                new Post(measuresUrl, connectTimeout, timeout, headers, new Payload()
//                        .addMeasurement("split", 45,
//                                new Tag("tagName", "tagValue")))));
//    }
//
//    @Test
//    public void testPostsATaggedMeasure() throws Exception {
//        client.postMeasures(new Measures()
//                .add(new Measure("foo", 42, new Tag("x", "y"))));
//        assertThat(poster.posts).isEqualTo(asList(
//                new Post(measuresUrl, connectTimeout, timeout, headers, new Payload()
//                        .addMeasurement("foo", 42, new Tag("x", "y")))));
//
//    }
//
//    @Test
//    public void testPostsComplexGauge() throws Exception {
//        client.postMeasures(new Measures()
//                .add(new Measure("foo", 100, 10, 20, 40, new Tag("x", "y"))));
//        assertThat(poster.posts).isEqualTo(asList(
//                new Post(measuresUrl, connectTimeout, timeout, headers, new Payload()
//                        .addMeasurement("foo", null, 100, 10, 20, 40, new Tag("x", "y")))));
//    }
//
//    @Test
//    public void testNothingPosts() throws Exception {
//        FakePoster poster = new FakePoster();
//        AppopticsClient client = AppopticsClient.builder("token")
//                .setPoster(poster)
//                .build();
//        PostMeasuresResult result = client.postMeasures(new Measures());
//        assertThat(result.results).isEmpty();
//        assertThat(poster.posts).isEmpty();
//    }
//
//    @Test
//    public void testVerifiesToken() throws Exception {
//        ensureIllegalArgument(new Runnable() {
//            @Override
//            public void run() {
//                AppopticsClient.builder(null).build();
//            }
//        });
//        AppopticsClient.builder("token").build();
//    }

}
