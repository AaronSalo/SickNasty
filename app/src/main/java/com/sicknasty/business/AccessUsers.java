package com.sicknasty.business;

import com.sicknasty.application.Service;
import com.sicknasty.objects.User;
import com.sicknasty.persistence.UserPersistence;
import com.sicknasty.persistence.stubs.UserPersistenceStub;

public class AccessUsers {

    private UserPersistence userHandler;


    public AccessUsers(){
        userHandler= Service.getUserData();
    }

    public User insertUser(String userName,String password){
        if(userHandler.getUser(userName)==null){  //if the user does not exist in stub
            return userHandler.insertNewUser(userName,password);
        }
                                                                //else return null
        return null;
    }

    public boolean updateUserPassword(User user,String oldPassword,String newPassword){
        if(user!=null && oldPassword!=null && newPassword!=null){
            if(oldPassword.equals(user.getPassword())){
                   user.changePassword(newPassword);                //change the password
                   return true;
            }
            else{
                //old password is not correct //error
                return false;
            }
        }
        return false;
    }
    public boolean updateUsername(User user,String newUsername){
        if(user!=null && newUsername!=null){
            if(userHandler.getUser(newUsername)==null){         //if user with newUserName does not exist (then change it)
                user.changeUsername(newUsername);               //change the password
                return true;
            }
            else{
                //new userName user already exists //show error
                return false;
            }
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
