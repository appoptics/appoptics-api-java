package com.appoptics.metrics.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
class Payload {
    static final ObjectMapper mapper = new ObjectMapper();
    @JsonProperty
    Integer period;
    @JsonProperty
    Long time;
    @JsonProperty
    List<Map<String, Object>> measurements = new LinkedList<Map<String, Object>>();

    public Payload setPeriod(Integer period) {
        this.period = period;
        return this;
    }

    public Payload setTime(long time) {
        this.time = time;
        return this;
    }

    public Payload addMeasurement(String name, double value, Tag... tags) {
        return addMeasurement(name, null, value, 1, value, value, tags);
    }

    public Payload addMeasurement(String name, Integer period, double value, Tag... tags) {
        return addMeasurement(name, period, value, 1, value, value, tags);
    }

    public Payload addMeasurement(String name, Integer period, double sum, long count, double min, double max, Tag... tags) {
        return addMeasurement(name, period, null, sum, count, min, max, tags);
    }

    public Payload addMeasurement(String name, Integer period, Long epoch, double sum, long count, double min, double max, Tag... tags) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", name);
        map.put("sum", sum);
        map.put("count", count);
        map.put("min", min);
        map.put("max", max);
        if (period != null) {
            map.put("period", period);
        }
        if (epoch != null) {
            map.put("time", epoch);
        }
        Map<String, String> tagMap = new HashMap<String, String>();
        map.put("tags", tagMap);
        for (Tag tag : tags) {
            tagMap.put(tag.name, tag.value);
        }
        return addMeasurement(map);
    }

    private Payload addMeasurement(Map<String, Object> map) {
        measurements.add(map);
        return this;
    }

    public byte[] serialize() {
        try {
            return mapper.writeValueAsBytes(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
