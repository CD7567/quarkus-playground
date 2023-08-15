package com.cd7567.quarkus_playground.services.hello_service.impl;

import com.cd7567.quarkus_playground.conf.HelloConfPojo;
import com.cd7567.quarkus_playground.services.hello_service.HelloService;
import com.cd7567.quarkus_playground.services.hello_service.qualifiers.Greeted;
import com.cd7567.quarkus_playground.services.hello_service.qualifiers.Regular;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;

/**
 * Regular greeting delivery service
 */
@RequestScoped
@Regular
public class RegularService implements HelloService {
    // Pojo with message configuration
    @Inject
    HelloConfPojo pojo;

    // Greeting event
    @Inject
    @Greeted
    Event<Visitor> greetedVisitorEvent;

    @Override
    public String getGreeting(String name) {
        greetedVisitorEvent.fire(new Visitor(name, this.getClass().getSimpleName()));
        return pojo.message().regular() + name;
    }
}
