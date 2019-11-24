package com.example.restaurant.manager;

import com.example.restaurant.entity.User;
import com.example.restaurant.utils.BCrypt;
import org.hibernate.SessionFactory;


public class UserManager extends GenericManager<User, Long> {

    public UserManager(SessionFactory sf) {
        super(User.class, sf);
    }

    public User getByEmailPassword(String email, String password) {

        User exampleEntity = new User();
        exampleEntity.setEmail(email);

        User user = this.getByExample(exampleEntity);

        if (user != null && BCrypt.checkpw(password, user.getPassword())) {
            return user;
        } else return null;

    }

}

