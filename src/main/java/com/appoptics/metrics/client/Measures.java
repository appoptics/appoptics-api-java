package com.appoptics.metrics.client;

import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents a bundle of measures
 */
@Getter
public class Measures {
    private final Long epoch;
    private final Integer period;
    private final List<Tag> tags = new LinkedList<>();
    private final List<Measure> measures = new LinkedList<>();

    public Measures() {
        this.epoch = null;
        this.period = null;
    }

    public Measures(List<Tag> tags, Long epoch) {
        this.tags.addAll(tags);
        this.epoch = epoch;
        this.period = null;
    }

    public Measures(List<Tag> tags, Long epoch, Integer period) {
        this.tags.addAll(tags);
        this.epoch = epoch;
        this.period = period;
    }

    public Measures(Measures measures, List<Measure> batch) {
        this.epoch = measures.epoch;
        this.period = measures.period;
        this.measures.addAll(batch);
        this.tags.addAll(measures.tags);
    }

    public List<Measures> partition(int size) {
        if (size > measures.size()) {
            return List.of(this);
        }
        List<Measures> result = new LinkedList<>();
        for (var batch : Lists.partition(this.measures, size)) {
            result.add(new Measures(this, batch));
        }
        return result;
    }

    public Measures add(Measure measure) {
        this.measures.add(measure);
        return this;
    }

    public boolean isEmpty() {
        return measures.isEmpty();
    }
}
