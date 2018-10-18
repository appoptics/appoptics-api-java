package com.appoptics.metrics.client;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

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

    @Override
    public String toString() {
        return name + "=" + value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tag)) return false;
        Tag tag = (Tag) o;
        return Objects.equals(name, tag.name) &&
                Objects.equals(value, tag.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value);
    }
}
