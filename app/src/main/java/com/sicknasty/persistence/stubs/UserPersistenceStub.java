package com.sicknasty.persistence.stubs;

import java.util.HashMap;

import com.sicknasty.objects.User;
import com.sicknasty.persistence.UserPersistence;

public class UserPersistenceStub implements UserPersistence {
    private HashMap<String, User> users;

    public UserPersistenceStub() {
        this.users = new HashMap<String, User>();
    }

    @Override
    public User GetUser(String username) {
        if (username == null) return null;

        // this will return the User object at that id
        // will return null if the id does not exist
        return this.users.get(username);
    }

    @Override
    public User InsertNewUser(String username, String password) {
        if (username == null || password == null) return null;

        if (this.users.containsKey(username)) return null;

        // generate a new user object
        User newUser = new User(username, password);

        // put that new user into the hash table using its id as the key
        this.users.put(newUser.getUsername(), newUser);

        return newUser;
    }

    @Override
    public User InsertNewUser(User user) {
        if (user == null) return null;

        // cool thing is, is that this will return null on failure and the object on success
        return this.users.put(user.getUsername(), user);
    }
}