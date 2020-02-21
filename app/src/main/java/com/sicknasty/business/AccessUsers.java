package com.sicknasty.business;

import com.sicknasty.application.Service;
import com.sicknasty.objects.Exceptions.ChangeUsernameException;
import com.sicknasty.objects.Exceptions.PasswordErrorException;
import com.sicknasty.objects.User;
import com.sicknasty.persistence.UserPersistence;
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
    public User insertUser(User user){
        return userHandler.insertNewUser(user);
    }


    public void updateUserPassword(String username,String oldPassword,String newPassword) throws PasswordErrorException {
        User user =userHandler.getUser(username);
        if(user == null) {
            throw new PasswordErrorException("User not found. Cannot change password.");
        } else {
            try {
                user.changePassword(newPassword);
            } catch (PasswordErrorException e) {
                throw e;
            } catch (Exception ex) {

            }
        }
    }
    /**
     * Updates username of a user if that username is available
     * @param user  the username we want to check,newUsername that we want to updarte
     * @return  true if the changing username was successful, false if not
     */
    public boolean updateUsername(User user,String newUsername) throws ChangeUsernameException {
        if(user!=null){                     //if the user is not null
                return user.changeUsername(newUsername);
        }
        return false;
    }

    /**
     * Checks to see if a given username is available for use
     * @param username  the username we want to check
     * @return  null if the username is not validvalid, else user
     */
    public User validNewUsername(String username){
        return userHandler.getUser(username);
    }

    public boolean deleteUser(String username){
        return userHandler.deleteUser(validNewUsername(username));
    }
}
