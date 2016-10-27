package com.lethallima.dropwizard.resources;


import com.codahale.metrics.annotation.Timed;
import com.lethallima.dropwizard.api.Saying;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;


/**
 * Created by LethalLima on 10/26/16.
 */

@Path("/hello-world")
@Produces(MediaType.APPLICATION_JSON)
public class HelloWorldResource {
    private final String template;
    private final String defaultName;
    private final AtomicLong counter;

    public HelloWorldResource(String template, String defaultName) {
        this.template = template;
        this.defaultName = defaultName;
        this.counter = new AtomicLong();
    }

    @GET
    @Timed
    public Saying sayHello(@QueryParam("name") String name) {
        final String value = String.format(template, (name == null || name.trim().isEmpty() ) ? defaultName : name);
        return new Saying(counter.incrementAndGet(), value);
    }
}