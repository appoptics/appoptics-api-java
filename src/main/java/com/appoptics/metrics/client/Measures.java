package com.appoptics.metrics.client;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents a bundle of measures
 */
public class Measures {
    private final Long epoch;
    private final Integer period;
    private final List<Tag> tags = new LinkedList<Tag>();
    private final List<IMeasure> measures = new LinkedList<IMeasure>();

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

    public Measures(Measures measures, List<IMeasure> batch) {
        this.epoch = measures.epoch;
        this.period = measures.period;
        this.measures.addAll(batch);
        this.tags.addAll(measures.tags);
    }

    public List<Measures> partition(int size) {
        List<Measures> result = new LinkedList<Measures>();
        for (List<IMeasure> batch : Lists.partition(this.measures, size)) {
            result.add(new Measures(this, batch));
        }
        return result;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public Measures add(Measure measure) {
        return addMeasure(measure);
    }

    private Measures addMeasure(IMeasure measure) {
        this.measures.add(measure);
        return this;
    }

    public Integer getPeriod() {
        return period;
    }

    public Long getEpoch() {
        return epoch;
    }

    public List<IMeasure> getMeasures() {
        return measures;
    }

    public boolean isEmpty() {
        return measures.isEmpty();
    }
}
