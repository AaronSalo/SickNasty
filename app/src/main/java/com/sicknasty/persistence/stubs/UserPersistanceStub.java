package com.sicknasty.persistance.stubs;

import java.util.ArrayList;
import java.util.HashMap;

import com.sicknasty.objects.User;
import com.sicknasty.persistance.UserPersistance;

public class UserPersistanceStub implements UserPersistance {
    private HashMap<String, User> users;

    public UserPersistanceStub() {
        this.users = new HashMap<String, User>();
    }

    @Override
    public User GetUserByUsername(String username) {
        return this.users.get(username);
    }

    @Override
    public User InsertNewUser(String username, String hashedPassword) {
        // check if we have a user with the specified username already
        // if we don't, create a new user and return that object
        if (!this.users.containsKey(username)) {
            User newUser = new User(username, hashedPassword);

            this.users.put(username, newUser);

            return newUser;
        }

        return null;
    }

    @Override
    public User InsertNewUser(User user) {
        // waiting on getter for username
        if (!this.users.containsKey(user.getUsername())) {
            this.users.put(user.getUsername(), user);

            return user;
        }

        return null;
    }
}