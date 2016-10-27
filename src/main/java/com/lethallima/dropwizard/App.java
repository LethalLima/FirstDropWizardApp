package com.lethallima.dropwizard;


import com.lethallima.dropwizard.health.TemplateHealthCheck;
import com.lethallima.dropwizard.resources.HelloWorldResource;
import com.lethallima.dropwizard.resources.HomeResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 * Created by LethalLima on 10/26/16.
 */
public class App extends Application<Config> {
    public static void main(String[] args) throws Exception {
        new App().run(args);
    }

    @Override
    public void initialize(Bootstrap<Config> bootstrap) {

    }

    @Override
    public void run(Config config, Environment environment) {
        final HelloWorldResource resource = new HelloWorldResource(
                config.getTemplate(),
                config.getDefaultName()
        );

        environment.jersey().register(new HomeResource());
        environment.jersey().register(resource);

        final TemplateHealthCheck templateHealthCheck = new TemplateHealthCheck(
                config.getTemplate()
        );

        environment.healthChecks().register("template", templateHealthCheck);
    }
}
