package com.cd7567.quarkus_playground;

import com.cd7567.quarkus_playground.services.hello_service.HelloService;
import com.cd7567.quarkus_playground.services.hello_service.impl.Visitor;
import com.cd7567.quarkus_playground.services.hello_service.qualifiers.Greeted;
import com.cd7567.quarkus_playground.services.hello_service.qualifiers.Polite;
import com.cd7567.quarkus_playground.services.hello_service.qualifiers.Regular;
import io.quarkus.arc.log.LoggerName;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.jboss.logging.Logger;

/**
 * Sample hello service
 */
@Path("/hello")
public class HelloResource {
    // JBoss Logger injection
    @LoggerName("HelloResource")
    Logger log;

    // Service for polite greeting delivery
    @Inject
    @Polite
    HelloService politeService;

    // Service for regular greeting delivery
    @Inject
    @Regular
    HelloService regularService;

    /**
     * REST endpoint that sends polite personified greeting
     * @param name -- name for personification
     * @return TEXT_PLAIN to send in response
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/polite/{name}")
    public String politeGreeting(@PathParam("name") String name) {
        log.info("Received GET on /hello/polite/" + name);
        return politeService.getGreeting(name);
    }

    /**
     * REST endpoint that sends regular personified greeting
     * @param name -- name for personification
     * @return TEXT_PLAIN to send in response
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/regular/{name}")
    public String regularGreeting(@PathParam("name") String name) {
        log.info("Received GET on /hello/regular/" + name);
        return regularService.getGreeting(name);
    }

    /**
     * REST endpoint that sends non-personified greeting
     * @return TEXT_PLAIN to send in response
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello from RESTEasy Reactive";
    }

    /**
     * Observer waiting for greeting events
     * @param visitor -- User that was greeted
     */
    void onGreet(@Observes @Greeted Visitor visitor) {
        log.infof("Visitor %s was greeted by %s", visitor.name, visitor.greetedBy);
    }
}
