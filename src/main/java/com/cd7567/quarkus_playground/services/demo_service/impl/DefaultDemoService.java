package com.cd7567.quarkus_playground.services.demo_service.impl;

import com.cd7567.quarkus_playground.conf.MapConfPojo;
import com.cd7567.quarkus_playground.services.demo_service.DemoService;
import com.cd7567.quarkus_playground.services.hello_service.HelloService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Any;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Map;

/**
 * Separate service for tiny demonstrations
 */
@RequestScoped
public class DefaultDemoService implements DemoService {
    // Mapped config
    @Inject
    MapConfPojo mapConfPojo;

    // Instance of HelloService for injection lookup demonstration
    @Inject
    @Any
    Instance<HelloService> instance;

    @Override
    public Map<String, String> demoMappedConfig() {
        return mapConfPojo.map();
    }

    @Override
    public List<String> demoInjectionLookup() {
        return instance.stream().map(it -> it.getClass().getName()).toList();
    }
}
