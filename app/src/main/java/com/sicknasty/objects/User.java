package com.sicknasty.objects;

import com.sicknasty.application.Service;

import java.util.ArrayList;


//@project sicknasty
///@author aaron salo
//the user object manages the each individual user account. Stores a password object, and other
//things related to the user
public class User {

    private String name; //the "display name" of the user
    private String userName; //used for storage, searching, etc.
    private Password password; //MAKE SURE we store a hashed version

    //ArrayList<User> followers; //list of people that follow the user
    ArrayList<User> follows; //list of people that the user follows

    PersonalPage personalPage; //the users personal page *****WAITING FOR PAGE IMPLEMENTATON****

    private final int MAX_USERNAME_LENGTH = 12; //username cannot be longer than 12 characters
    private final int MIN_USERNAME_LENGTH = 3; //username must be at least 3 characters

    public User(String name, String username, String newPassword)throws PasswordErrorException, UserCreationException,
            ChangeNameException, ChangeUsernameException {
        changeName(name);
        changeUsername(username);
        password = new Password(newPassword);
        personalPage = new PersonalPage(this); //create a personal page for this user
    }//end of constructor


    public String getName(){ return name;}

    public PersonalPage getPersonalPage() {return this.personalPage;}

    /**
     * change the display name of the user, checking some parameters
     * @param newName   the name we want to change to
     * @return true on success
     */
    public boolean changeName(String newName) throws ChangeNameException{
        boolean success = false;
        if(newName.length() < MAX_USERNAME_LENGTH) {
            name = newName;
            success = true;
        } else
            throw new ChangeNameException("The username was too long; must be shorter than "+ MAX_USERNAME_LENGTH);
        return success;
    }

    public String getUsername(){return userName;}

    /**
     * Pass a new password through a hashing function
     * @return  true on success
     */
    public void changePassword(String newPass) {
        //return password.changePassword(newPass); //password class will handle the password change
    }//end of change password


    public boolean checkPasswordCorrect(String inputPass){
        return password.checkPass(inputPass); //check if the password is correct
    }


    /**change the user name
     * there is some error checking to see if the username isn't too long, or isn't used by anyone
     * etc. If the checks fail, it will return false and do nothing, otherwise, change the username
     * @return true on success
     */
    public void changeUsername(String newUsername) throws ChangeUsernameException{
        boolean success = false;
        //check if newUsername is used by anyone
        if(newUsername!=null) {
            if (Service.getUserData().getUser(newUsername) == null) { //is the username used by anyone else
                if ((newUsername.length() <= MAX_USERNAME_LENGTH) &&
                        (newUsername.length() >= MIN_USERNAME_LENGTH)) { //is the newUsername an appropriate length
                    if (!newUsername.contains(" ")) { //check to see if the string contains whitespace
                        Service.getUserData().updateUsername(userName,newUsername);
                        userName = newUsername;
                    } else
                        throw new ChangeUsernameException("Username cannot contain whitespace");
                } else
                    throw new ChangeUsernameException("Username must be longer than " + MIN_USERNAME_LENGTH +
                            " and shorter than " + MAX_USERNAME_LENGTH);
            } else
                throw new ChangeUsernameException("Username is already taken");
        } else
            throw new ChangeUsernameException("An unknown error occured when trying to update the username");
    } //changeUsername

    //if you need an explanation for this one.... idk man....
//    //public void addFollower(User newFollower) {
//        followers.add(newFollower);
//    }//addFollower


    //add a new user to the list of users you follow
    public void follow(User newFollow) {
        follows.add(newFollow);
    }//follow

} //end of class

class ChangeNameException extends Exception{
    public ChangeNameException(String message) {
        super(message);
    }
}

class UserCreationException extends Exception {

    public UserCreationException(String message) {
        super(message);
    }
}

class ChangeUsernameException extends Exception {

    public ChangeUsernameException(String message) {
        super(message);
    }
}