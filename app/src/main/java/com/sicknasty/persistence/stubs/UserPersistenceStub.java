package com.sicknasty.persistence.stubs;

import java.util.HashMap;

import com.sicknasty.objects.Password;
import com.sicknasty.objects.User;
import com.sicknasty.persistence.UserPersistence;

public class UserPersistenceStub implements UserPersistence {
    private HashMap<Integer, User> users;

    public UserPersistenceStub() {
        this.users = new HashMap<Integer, User>();
    }

    @Override
    public User GetUserById(int id) {
        // this will return the User object at that id
        // will return null if the id does not exist
        return this.users.get(id);
    }

    @Override
    public User InsertNewUser(String username, String password) {
        // generate a new user object
        User newUser = new User(username, password);

        // put that new user into the hash table using its id as the key
        this.users.put(newUser.getUserID(), newUser);

        return newUser;
    }

    @Override
    public User InsertNewUser(User user) {
        // cool thing is, is that this will return null on failure and the object on success
        return this.users.put(user.getUserID(), user);
    }
}