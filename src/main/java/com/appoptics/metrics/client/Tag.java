package com.appoptics.metrics.client;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class Tag {
    @JsonProperty("name")
    public final String name;
    @JsonProperty("value")
    public final String value;

    @JsonCreator
    public Tag(@JsonProperty("name") String name,
               @JsonProperty("value") String value) {
        this.name = name;
        this.value = value;
    }
}
