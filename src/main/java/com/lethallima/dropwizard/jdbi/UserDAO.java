package com.lethallima.dropwizard.jdbi;

import com.lethallima.dropwizard.core.User;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Created by LethalLima on 10/29/16.
 */
public class UserDAO extends AbstractDAO<User> {

    public UserDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public User createUser(User user) {
        return super.persist(user);
    }

    @SuppressWarnings("unchecked")
    public List<User> getAllUsers() {
        return super.criteria().list();
    }
}
