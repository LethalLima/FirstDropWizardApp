package com.lethallima.dropwizard.auth;

import com.lethallima.dropwizard.core.User;
import com.lethallima.dropwizard.jdbi.UserDAO;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;
import io.dropwizard.hibernate.UnitOfWork;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Optional;

/**
 * Created by LethalLima on 10/30/16.
 */
public class BCryptAuthenticator implements Authenticator<BasicCredentials, User> {

    private final UserDAO userDAO;

    public BCryptAuthenticator(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @UnitOfWork
    @Override
    public Optional<User> authenticate(BasicCredentials basicCredentials) throws AuthenticationException {
        User user = userDAO.getUserByUsername(basicCredentials.getUsername());

        if(BCrypt.checkpw(basicCredentials.getPassword(), user.getPassword())) {
            return Optional.of(user);
        }

        return Optional.empty();
    }
}
