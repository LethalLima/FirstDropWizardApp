package com.lethallima.dropwizard.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by LethalLima on 10/26/16.
 */

@Path("/")
public class HomeResource {
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getHome() {
        return "Welcome to my awesome micro service. -Jeff";
    }
}
