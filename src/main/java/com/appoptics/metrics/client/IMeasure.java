package com.appoptics.metrics.client;

import java.util.Map;

/**
 * Represents a client
 */
public interface IMeasure {
    Map<String,Object> toMap();
}
