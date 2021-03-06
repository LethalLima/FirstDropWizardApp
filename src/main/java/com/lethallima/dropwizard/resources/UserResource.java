package com.lethallima.dropwizard.resources;

import com.lethallima.dropwizard.core.User;
import com.lethallima.dropwizard.jdbi.UserDAO;
import com.lethallima.dropwizard.utilities.Constants;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;


import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by LethalLima on 10/29/16.
 */
@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private final UserDAO userDAO;

    public UserResource(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @GET
    @UnitOfWork
    @RolesAllowed(Constants.ADMIN_USER)
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    @POST
    @UnitOfWork
    @RolesAllowed(Constants.ADMIN_USER)
    public User createUser(@Auth User user) {
        return userDAO.createUser(user);
    }
}
