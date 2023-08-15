package com.cd7567.quarkus_playground.services.hello_service;

/**
 * Interface for greeting delivery services
 */
public interface HelloService {
    /**
     * Function to obtain corresponddong greeting
     * @param name -- greeted username
     * @return greeting
     */
    String getGreeting(String name);
}
