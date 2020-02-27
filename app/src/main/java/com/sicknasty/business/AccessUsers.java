package com.sicknasty.business;

import com.sicknasty.application.Service;
import com.sicknasty.objects.Exceptions.ChangeUsernameException;
import com.sicknasty.objects.Exceptions.PasswordErrorException;
import com.sicknasty.objects.Exceptions.UserNotFoundException;
import com.sicknasty.objects.User;
import com.sicknasty.persistence.UserPersistence;
import com.sicknasty.persistence.exceptions.DBUsernameExistsException;
import com.sicknasty.persistence.exceptions.DBUsernameNotFoundException;

/** @author jay
        * wrapper for the user db
        * passes info from UI to the db and vise versa
 */
public class AccessUsers {

    private UserPersistence userHandler;

    public AccessUsers(){
        userHandler= Service.getUserData();             //get the dataStub
    }


    //insert a user to the db
    public User insertUser(User user) throws DBUsernameExistsException {
        return userHandler.insertNewUser(user);
    }


    public void updateUserPassword(String username,String oldPassword,String newPassword) throws Exception {
        User user =userHandler.getUser(username);
        if(user == null) {
            throw new PasswordErrorException("User not found. Cannot change password.");
        } else {
            try {
                user.changePassword(newPassword);
            } catch (Exception ex) {
                throw ex; //rethrow the exception, handle it in the UI layer
            }
        }
    }
    /**
     * Updates username of a user if that username is available
     * @param user  the username we want to check,newUsername that we want to updarte
     * @return  true if the changing username was successful, false if not
     */
    public void updateUsername(User user,String newUsername) throws ChangeUsernameException {
        try {
            user.changeUsername(newUsername);
        } catch (ChangeUsernameException e) {
            throw e;
        }
    }


    public User getUser(String username) throws UserNotFoundException, DBUsernameNotFoundException {
        User user = userHandler.getUser(username);
        if(user != null)
            return user;
        else
            throw new UserNotFoundException("Could not find a user with that username");
    }



    /**
     * Checks to see if a given username is available for use
     * @param username  the username we want to check
     * @return  false if the username is taken and true if it is available
     */
    public boolean validNewUsername(String username) throws DBUsernameNotFoundException {
        return userHandler.getUser(username) != null;
    }

    public void deleteUser(String username) throws UserNotFoundException, DBUsernameNotFoundException {
        User user = userHandler.getUser(username);
        if(user != null)
            userHandler.deleteUser(user);
        else
            throw new UserNotFoundException("Could not find the specified user");
    }
}
