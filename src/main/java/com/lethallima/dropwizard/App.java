package com.lethallima.dropwizard;


import com.lethallima.dropwizard.auth.BCryptAuthenticator;
import com.lethallima.dropwizard.core.User;
import com.lethallima.dropwizard.health.TemplateHealthCheck;
import com.lethallima.dropwizard.jdbi.UserDAO;
import com.lethallima.dropwizard.resources.HomeResource;
import com.lethallima.dropwizard.resources.UserResource;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.UnitOfWorkAwareProxyFactory;
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
        // resoureces
        final UserDAO userDAO = new UserDAO(hibernateBundle.getSessionFactory());
        final UserResource userResource = new UserResource(userDAO);

        // auth
        BCryptAuthenticator bCryptAuth = new UnitOfWorkAwareProxyFactory(hibernateBundle)
                .create(BCryptAuthenticator.class, UserDAO.class, userDAO);

        // jersey resources
        environment.jersey().register(new HomeResource());
        environment.jersey().register(userResource);
        environment.jersey().register(new AuthDynamicFeature(new BasicCredentialAuthFilter.Builder<User>()
                .setAuthenticator(bCryptAuth)
                .setRealm("Auth Realm")
                .buildAuthFilter()));

        final TemplateHealthCheck templateHealthCheck = new TemplateHealthCheck(
                config.getTemplate()
        );

        environment.healthChecks().register("template", templateHealthCheck);
    }

    public static void main(String[] args) throws Exception {
        new App().run(args);
    }
}
