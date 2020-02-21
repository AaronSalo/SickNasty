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
    private String password; //MAKE SURE we store a hashed version

    //ArrayList<User> followers; //list of people that follow the user
    ArrayList<User> follows; //list of people that the user follows

    PersonalPage personalPage; //the users personal page *****WAITING FOR PAGE IMPLEMENTATON****

    public final int MAX_NAME_LENGTH = 15;
    public final int MIN_NAME_LENGTH = 2;

    private final int MAX_USERNAME_LENGTH = 12; //username cannot be longer than 12 characters
    private final int MIN_USERNAME_LENGTH = 3; //username must be at least 3 characters

    public User(String name, String username, String newPassword)throws UserCreationException,ChangeUsernameException,PasswordLengthException{
        this.name=changeName(name);
        changeUsername(username);
        changePassword(newPassword);
        personalPage = new PersonalPage(this); //create a personal page for this user
    }//end of constructor


    public String getName(){ return name;}

    public PersonalPage getPersonalPage() {return this.personalPage;}

    /**
     * change the display name of the user, checking some parameters
     * @param newName   the name we want to change to
     * @return true on success
     */
    public String changeName(String newName) throws UserCreationException{
        if(newName.length()>=MIN_NAME_LENGTH && newName.length() <=MAX_NAME_LENGTH) {
            return name;
        } else
            throw new UserCreationException("The name should be in between 6 and 15 characters");
    }

    public String getUsername(){return userName;}

    public void changePassword(String newPass) throws PasswordLengthException{
        if(newPass!=null){
            if(newPass.length()>=5 && newPass.length()<=15)
                password=newPass;
            else
                throw new PasswordLengthException("Password Length should be between 5 to 15 characters");
        }
    }//end of change password


    public boolean checkPasswordCorrect(String inputPass){
        return password.equals(inputPass); //check if the password is correct
    }

    /**change the user name
     * there is some error checking to see if the username isn't too long, or isn't used by anyone
     * etc. If the checks fail, it will return false and do nothing, otherwise, change the username
     * @return true on success
     */
    public void changeUsername(String newUsername) throws ChangeUsernameException{
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

    //add a new user to the list of users you follow
    public void follow(User newFollow) {
        follows.add(newFollow);
    }//follow

} //end of class

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
class PasswordLengthException extends Exception {

    public PasswordLengthException(String message) {
        super(message);
    }
}