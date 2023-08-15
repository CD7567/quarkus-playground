package com.cd7567.quarkus_playground.services.hello_service.impl;

/**
 * Pojo representation of user
 */
public class Visitor {
    public String name;
    public String greetedBy;

    public Visitor(String name, String greetedBy) {
        this.name = name;
        this.greetedBy = greetedBy;
    }
}
