package com.cd7567.quarkus_playground.services.demo_service;

import java.util.List;
import java.util.Map;

/**
 * Separate service for tiny demonstrations
 */
public interface DemoService {
    /**
     * Demonstrate config -> map conversion
     * @return config Map
     */
    Map<String, String> demoMappedConfig();

    /**
     * Demonstrate injection lookup
     * @return List of available beans
     */
    List<String> demoInjectionLookup();
}
