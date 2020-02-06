package com.sicknasty.persistence;

import com.sicknasty.objects.User;

public interface UserPersistence {
    /**
     * This returns a User object specified by its unique Id
     *
     * @param   username the unique username of the User
     * @return          returns the User object if a user with specified username exists, otherwise null
     */
    public User getUser(String username);

    /**
     * Inserts a new User with by specifying a unique username and a hashed password.
     * 
     * @param   username the unique username of the new user
     * @param   password the plaintext password of user
     * 
     * @return          returns the new User object on success, otherwise null
     */
    public User insertNewUser(String username, String password);

    /**
     * Inserts a new User object
     * 
     * @param   user    the User object to insert
     * @return          returns the User object if successful, otherwise null
     */
    public User insertNewUser(User user);
}