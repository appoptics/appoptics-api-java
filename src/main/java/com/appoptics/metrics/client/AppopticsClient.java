package com.appoptics.metrics.client;

import java.net.URI;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;

/**
 * The main class that should be used to access the AppOptics API
 */
public class AppopticsClient {
    private static final String LIB_VERSION = Versions.getVersion("META-INF/maven/com.appoptics.metrics/appoptics-api-java/pom.properties", AppopticsClient.class);
    private final URI uri;
    private final int batchSize;
    private final IPoster poster;
    private final ExecutorService executor;
    private final ResponseConverter responseConverter = new ResponseConverter();
    private final Map<String, String> measurementPostHeaders = new HashMap<String, String>();

    public static AppopticsClientBuilder builder(String token) {
        return new AppopticsClientBuilder(token);
    }

    AppopticsClient(AppopticsClientAttributes attrs) {
        this.uri = URIs.removePath(attrs.uri);
        this.batchSize = attrs.batchSize;
        this.poster = attrs.poster;
        BlockingQueue<Runnable> queue = new SynchronousQueue<Runnable>();
        ThreadFactory threadFactory = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("appoptics-client");
                thread.setDaemon(true);
                return thread;
            }
        };
        this.executor = new ThreadPoolExecutor(0, attrs.maxInflightRequests, 10, TimeUnit.SECONDS, queue, threadFactory, new CallerRunsPolicy());
        measurementPostHeaders.put("Content-Type", "application/json");
        measurementPostHeaders.put("Authorization", Authorization.buildAuthHeader(attrs.token));
        measurementPostHeaders.put("User-Agent", String.format("%s appoptics-api-java/%s", attrs.agentIdentifier, LIB_VERSION));
    }

    public PostMeasuresResult postMeasures(Measures measures) {
        PostMeasuresResult result = new PostMeasuresResult();
        if (measures.isEmpty()) {
            return result;
        }
        Future<List<PostResult>> future = null;
        if (!measures.isEmpty()) {
            future = postMeasures("/v1/measurements", measures, responseConverter, new IBuildsPayload() {
                @Override
                public byte[] build(Measures measures) {
                    return buildPayload(measures);
                }
            });
        }
        if (future != null) {
            result.results.addAll(Futures.get(future));
        }
        return result;
    }

    private Future<List<PostResult>> postMeasures(final String uri,
                                                  final Measures measures,
                                                  final ResponseConverter responseConverter,
                                                  final IBuildsPayload payloadBuilder) {
        return executor.submit(new Callable<List<PostResult>>() {
            @Override
            public List<PostResult> call() throws Exception {
                List<PostResult> results = new LinkedList<PostResult>();
                for (Measures batch : measures.partition(AppopticsClient.this.batchSize)) {
                    byte[] payload = payloadBuilder.build(batch);
                    try {
                        var response = poster.post(fullUrl(uri), measurementPostHeaders, payload);
                        results.add(responseConverter.convert(payload, response));
                    } catch (Exception e) {
                        results.add(responseConverter.convert(payload, e));
                    }
                }
                return results;
            }
        });
    }

    private byte[] buildPayload(Measures measures) {
        final Map<String, Object> payload = new HashMap<String, Object>();
        Maps.putIfNotNull(payload, "time", measures.getEpoch());
        Maps.putIfNotNull(payload, "period", measures.getPeriod());
        if (!measures.getTags().isEmpty()) {
            payload.put("tags", Tags.toMap(measures.getTags()));
        }
        List<Map<String, Object>> gauges = new LinkedList<Map<String, Object>>();
        for (IMeasure measure : measures.getMeasures()) {
            Map<String, Object> measureMap = measure.toMap();
            gauges.add(measureMap);
        }
        Maps.putIfNotEmpty(payload, "measurements", gauges);
        return Json.serialize(payload);
    }

    private String fullUrl(String path) {
        return this.uri.toString() + path;
    }
}
