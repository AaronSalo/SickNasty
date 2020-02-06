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
    public User getUser(String username) {
        if (username == null) return null;

        // this will return the User object at that id
        // will return null if the id does not exist
        return this.users.get(username);
    }

    @Override
    public User insertNewUser(User user) {
        if (user == null) return null;

        // cool thing is, is that this will return null on failure and the object on success
        return this.users.put(user.getUsername(), user);
    }

    @Override
    public boolean deleteUser(User user) {
	if (user == null) return false;

	User result = this.users.remove(user.getUsername());

	return result != null;
    }
}
