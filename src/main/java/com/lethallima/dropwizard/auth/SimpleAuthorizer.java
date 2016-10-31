package com.lethallima.dropwizard.auth;

import com.lethallima.dropwizard.core.User;
import io.dropwizard.auth.Authorizer;

/**
 * Created by LethalLima on 10/30/16.
 */
public class SimpleAuthorizer implements Authorizer<User> {
    @Override
    public boolean authorize(User user, String role) {
        return user.getRole().equals(role);
    }
}
