package com.cd7567.quarkus_playground;

import io.quarkus.arc.log.LoggerName;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.*;
import org.jboss.logging.Logger;

/**
 * Resource that represents sending params in different ways
 */
@Path("/param")
public class ParamResource {
    // JBoss Logger injection
    @LoggerName("ParamResource")
    Logger log;

    // UriInfo to get params from URI
    @Inject
    UriInfo info;

    /**
     * REST endpoint that demonstrates usage of URI params
     * @return MultivaluedMap of params
     */
    @GET
    @Path("/uri")
    @Produces(MediaType.TEXT_PLAIN)
    public String urlParams() {
        log.info("Received GET on /param/uri/");
        return info.getQueryParameters().toString();
    }

    /**
     * REST endpoint that demonstrates usage of custom headers
     * @return MultivaluedMap of custom headers
     */
    @GET
    @Path("/header")
    @Produces(MediaType.TEXT_PLAIN)
    public String headerParams(@Context HttpHeaders headers) {
        log.info("Received GET on /param/header/");

        MultivaluedMap<String, String> filteredHeaders = new MultivaluedHashMap<>();

        headers.getRequestHeaders().entrySet()
                .stream()
                .filter(it -> it.getKey().startsWith("X"))
                .forEach(
                        entry -> filteredHeaders.addAll(entry.getKey(), entry.getValue())
                );

        return filteredHeaders.toString();
    }

    /**
     * REST endpoint that demonstrates usage of method body
     * @return Method body
     */
    @GET
    @Path("/body")
    @Produces(MediaType.TEXT_PLAIN)
    public String bodyParams(String body) {
        log.info("Received GET on /param/body/");
        return body;
    }
}
