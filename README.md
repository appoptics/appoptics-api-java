# appoptics-java

Java language bindings for the [AppOptics](https://www.appoptics.com) Metrics API.

## Maven Dependency

    <dependency>
        <groupId>com.appoptics.metrics</groupId>
        <artifactId>appoptics-java</artifactId>
        <version>1.0.0</version>
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
        .add(new TaggedMeasure(name, value, tag, tag))
        .add(new TaggedMeasure(name, sum, count, min, max, tag, tag ,tag)));
    
    for (PostResult postResult : result.results) {
        if (result.isError()) {
            log.error(result.toString);
        }
    }

