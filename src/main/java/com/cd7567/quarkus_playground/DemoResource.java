package com.cd7567.quarkus_playground;

import com.cd7567.quarkus_playground.services.demo_service.DemoService;
import io.quarkus.arc.log.LoggerName;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.Map;

/**
 * Resource for simple demonstrations
 */
@Path("/demo")
public class DemoResource {
    // JBoss Logger injection
    @LoggerName("DemoResource")
    Logger log;

    // DemoService that provides demonstrations
    @Inject
    DemoService demoService;

    /**
     * REST endpoint that prints map representation of 'test' config category
     * @return config Map
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/map-conf")
    public Map<String, String> listConfig() {
        log.info("Received GET on /demo/map-conf");
        return demoService.demoMappedConfig();
    }

    /**
     * REST endpoint that prints results of programmatic lookup
     * @return List of possible options
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/inject-lookup")
    public List<String> listInject() {
        log.info("Received GET on /demo/inject-lookup");
        return demoService.demoInjectionLookup();
    }
}
