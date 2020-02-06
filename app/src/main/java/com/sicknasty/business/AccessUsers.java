package com.sicknasty.business;

import com.sicknasty.application.Service;
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

    public User insertUser(String name, String userName,String password){
            return userHandler.insertNewUser(new User(name,userName,password));
    }

    public boolean updateUserPassword(String username,String oldPassword,String newPassword){
        User user =userHandler.getUser(username);
        if(true){                   //check the password
            return user.changePassword(newPassword);
        }
        return false;
    }
    /**
     * Updates username of a user if that username is available
     * @param user  the username we want to check,newUsername that we want to updarte
     * @return  true if the changing username was successful, false if not
     */
    public boolean updateUsername(User user,String newUsername){
        if(user!=null){                     //if the user is not null
                return user.changeUsername(newUsername);
        }
        return false;
    }

    /**
     * Checks to see if a given username is available for use
     * @param username  the username we want to check
     * @return  true if the username is valid, false if not
     */
    public boolean validNewUsername(String username){
        if(userHandler.getUser(username)!=null)
            return true;
        return false;
    }
}
