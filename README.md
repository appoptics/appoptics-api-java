# appoptics-api-java

Java language bindings for the [AppOptics](https://www.appoptics.com) Metrics 
[API](https://docs.appoptics.com/api/#create-a-measurement).

## Maven Dependency

Find the latest [version here](https://search.maven.org/search?q=g:com.appoptics.metrics%20AND%20a:appoptics-api-java&core=gav).

    <dependency>
        <groupId>com.appoptics.metrics</groupId>
        <artifactId>appoptics-api-java</artifactId>
        <version>1.0.2</version>
    </dependency>

## Setup

You must first initialize the client:

    AppopticsClient client = AppopticsClient.builder(apiToken)
        // these are optional
        .setConnectTimeout(new Duration(5, SECONDS))
        .setReadTimeout(new Duration(5, SECONDS))
        .setAgentIdentifier("my app name")
        // and finally build
        .build();
    
Once your client has been built, you can submit measures to the AppOptics
API:

    PostMeasuresResult result = client.postMeasures(new Measures()
        .add(new Measure(name, value, tag, tag))
        .add(new Measure(name, sum, count, min, max, tag, tag ,tag)));
    
    for (PostResult postResult : result.results) {
        if (result.isError()) {
            log.error(result.toString);
        }
    }

