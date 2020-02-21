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


    //throwing exceptions everywhere
    public void insertUser(String name, String userName,String password){
        try {
            userHandler.insertNewUser(new User(name,userName,password));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //ahh throw exceptions
    public void updateUserPassword(String username,String oldPassword,String newPassword){
        User user =userHandler.getUser(username);
        if(user.checkPasswordCorrect(oldPassword)){                   //check the password
            try {
                user.changePassword(newPassword);
            } catch (Exception e) {
                e.printStackTrace();                                //don't just print it (do something)
            }
        }
    }
    /**
     * Updates username of a user if that username is available
     * @param user  the username we want to check,newUsername that we want to updarte
     * @return  true if the changing username was successful, false if not
     */
    public void updateUsername(User user,String newUsername){
        if(user!=null){                     //if the user is not null
            try {
                user.changeUsername(newUsername);
            } catch (Exception e) {
                e.printStackTrace();                                //don't just print it (do something)
            }
        }
    }

    //have to revisit this!!!!!!
    /**
     * Checks to see if a given username is available for use
     * @param username  the username we want to check
     * @return  null if the username is not validvalid, else user
     */
    public User validNewUsername(String username){
        return userHandler.getUser(username);
    }

    //what to return (Revisit this??)
    public boolean deleteUser(String username){
        return userHandler.deleteUser(validNewUsername(username));
    }
}
