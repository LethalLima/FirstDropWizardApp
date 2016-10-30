package com.lethallima.dropwizard;


import com.lethallima.dropwizard.core.User;
import com.lethallima.dropwizard.health.TemplateHealthCheck;
import com.lethallima.dropwizard.jdbi.UserDAO;
import com.lethallima.dropwizard.resources.HomeResource;
import com.lethallima.dropwizard.resources.UserResource;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 * Created by LethalLima on 10/26/16.
 */
public class App extends Application<Config> {

    private final HibernateBundle<Config> hibernateBundle =
            new HibernateBundle<Config>(User.class) {
                @Override
                public DataSourceFactory getDataSourceFactory(Config configuration) {
                    return configuration.getDataSourceFactory();
                }
            };

    @Override
    public void initialize(Bootstrap<Config> bootstrap) {
        bootstrap.addBundle(hibernateBundle);
    }

    @Override
    public void run(Config config, Environment environment) {
        final UserDAO userDAO = new UserDAO(hibernateBundle.getSessionFactory());
        final UserResource userResource = new UserResource(userDAO);

        environment.jersey().register(new HomeResource());
        environment.jersey().register(userResource);

        final TemplateHealthCheck templateHealthCheck = new TemplateHealthCheck(
                config.getTemplate()
        );

        environment.healthChecks().register("template", templateHealthCheck);
    }

    public static void main(String[] args) throws Exception {
        new App().run(args);
    }
}
