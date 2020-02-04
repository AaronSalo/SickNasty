package com.sicknasty.persistence;

import com.sicknasty.objects.User;

public interface UserPersistance {
    /**
     * This returns a User object specified by a username
     * 
     * @param   username the username to search for
     * @return          returns the User object if a user with specified username exists, otherwise null
     */
    public User GetUserByUsername(String username);

    /**
     * Inserts a new User with by specifying a unique username and a hashed password.
     * 
     * @param   username the unique username of the new user
     * @param   hashedPassword the salt and hashed password
     * 
     * @return          returns the new User object on success, otherwise null
     */
    public User InsertNewUser(String username, String hashedPassword);

    /**
     * Inserts a new User object
     * 
     * @param   user    the User object to insert
     * @return          returns the User object if successful, otherwise null
     */
    public User InsertNewUser(User user);
}