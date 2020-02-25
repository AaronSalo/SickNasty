package com.sicknasty.persistence;

import com.sicknasty.objects.Exceptions.UserNotFoundException;
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
     * Inserts a new User object
     * 
     * @param   user    the User object to insert
     * @return          returns the User object if successful, otherwise null
     */
    public User insertNewUser(User user);

    /**
     * Deletes a User by specifying a User object
     *
     * @param	user	the User object to delete
     * @return		returns true if an object was deleted, otherwise false
     */
     public boolean deleteUser(User user);


     public boolean updateUsername(String old,String newOne);
}
