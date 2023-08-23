package com.appoptics.metrics.client;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.*;

@ToString
@EqualsAndHashCode
public class Measure {
    private final String name;
    Map<String, Object> metricAttributes = Collections.emptyMap();
    private Long epoch;
    private Integer period;
    private double sum;
    private long count;
    private double min;
    private double max;
    private List<Tag> tags = new LinkedList<>();

    public Measure(String name, double value, Tag...tags) {
        this(name, value, 1, value, value, tags);
    }

    public Measure(String name, double sum, long count, double min, double max, Tag...tags) {
        this.name = name;
        this.sum = sum;
        this.count = count;
        this.min = min;
        this.max = max;
        addTags(tags);
    }

    public void addTags(Tag... tags) {
        for (Tag tag : tags) {
            addTag(tag);
        }
    }

    public void addTag(Tag tag) {
        String tagName = sanitizeTagName(tag.name);
        String tagValue = sanitizeTagValue(tag.value);
        this.tags.add(new Tag(tagName, tagValue));
    }

    private String sanitizeTagValue(String value) {
        value = Sanitizer.TAG_VALUE_SANITIZER.apply(value);
        return trimToSize(value, 255);
    }

    private String sanitizeTagName(String name) {
        name = Sanitizer.TAG_NAME_SANITIZER.apply(name);
        return trimToSize(name, 64);
    }

    private String trimToSize(String string, int size) {
        if (string == null || string.length() <= size) {
            return string;
        }
        return string.substring(0, size);
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", Sanitizer.METRIC_NAME_SANITIZER.apply(name));
        Maps.putIfNotNull(map, "period", period);
        Maps.putIfNotEmpty(map, "attributes", metricAttributes);
        Maps.putIfNotNull(map, "time", epoch);
        map.put("sum", sum);
        map.put("count", count);
        map.put("min", min);
        map.put("max", max);
        if (!tags.isEmpty()) {
            map.put("tags", Tags.toMap(tags));
        }
        return map;
    }

    public Measure setPeriod(int period) {
        this.period = period;
        return this;
    }

    public Measure setEpoch(long epoch) {
        this.epoch = epoch;
        return this;
    }

    public Measure setMetricAttributes(Map<String, Object> attributes) {
        this.metricAttributes = attributes;
        return this;
    }
}
