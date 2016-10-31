package com.lethallima.dropwizard.jdbi;

import com.lethallima.dropwizard.core.User;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;

/**
 * Created by LethalLima on 10/29/16.
 */
public class UserDAO extends AbstractDAO<User> {

    public UserDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public User createUser(User user) {
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));

        return super.persist(user);
    }

    @SuppressWarnings("unchecked")
    public List<User> getAllUsers() {
        return super.criteria().list();
    }

    public User getUserByUsername(String username) {
        return (User) super.criteria().add(Restrictions.eq("username", username)).uniqueResult();
    }
}
