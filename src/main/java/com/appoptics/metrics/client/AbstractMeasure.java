package com.appoptics.metrics.client;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

abstract class AbstractMeasure implements IMeasure {
    final String name;
    Map<String, Object> metricAttributes = Collections.emptyMap();
    Integer period;
    Long epoch;

    public AbstractMeasure(AbstractMeasure measure) {
        this.name = measure.name;
        this.metricAttributes = measure.metricAttributes;
        this.period = measure.period;
        this.epoch = measure.epoch;
    }

    public AbstractMeasure(String name) {
        this.name = name;
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", Sanitizer.METRIC_NAME_SANITIZER.apply(name));
        Maps.putIfNotNull(map, "period", period);
        Maps.putIfNotEmpty(map, "attributes", metricAttributes);
        return map;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractMeasure)) return false;
        AbstractMeasure that = (AbstractMeasure) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(metricAttributes, that.metricAttributes) &&
                Objects.equals(period, that.period) &&
                Objects.equals(epoch, that.epoch);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, metricAttributes, period, epoch);
    }
}

