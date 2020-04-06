package com.sicknasty.business;

import com.sicknasty.application.Service;
import com.sicknasty.objects.Exceptions.ChangeUsernameException;
import com.sicknasty.objects.Exceptions.PasswordErrorException;
import com.sicknasty.objects.Exceptions.UserNotFoundException;
import com.sicknasty.objects.User;
import com.sicknasty.persistence.UserPersistence;
import com.sicknasty.persistence.exceptions.DBUsernameExistsException;
import com.sicknasty.persistence.exceptions.DBUsernameNotFoundException;

import java.util.ArrayList;

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

    public void updateUserPassword(String username,String newPassword) throws Exception {
        User user = this.userHandler.getUser(username);

        if (user == null) {
            throw new PasswordErrorException("User not found. Cannot change password.");
        } else {

                // update local copy and the database copy
                // note that order here is not important
                this.userHandler.updatePassword(user, newPassword);
        }
    }

    /**
     * Updates username of a user if that username is available
     * @param user  the username we want to check,newUsername that we want to updarte
     */
    public void updateUsername(User user,String newUsername) throws ChangeUsernameException, DBUsernameExistsException, DBUsernameNotFoundException {
        this.userHandler.updateUsername(user.getUsername(), newUsername);
    }

    public User getUser(String username) throws DBUsernameNotFoundException {
        return userHandler.getUser(username);
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
        if (user != null)
            userHandler.deleteUser(user);
        else
            throw new UserNotFoundException("Could not find the specified user");
    }

    /**

     * @return  all the users in DB so that user can search for a particular user
     */
    public ArrayList<String> getUsersByUsername(){
        return userHandler.getAllUsers();
    }

    public ArrayList<Message> getMessages(User user1, User user2){
        return userHandler.getMessages(user1, user2);
    }

    public void addMessage(Message message){
        userHandler.addMessage(message);
    }
}
