package com.sicknasty.objects;

import com.sicknasty.objects.Exceptions.ChangeNameException;
import com.sicknasty.objects.Exceptions.ChangeUsernameException;
import com.sicknasty.objects.Exceptions.PasswordErrorException;
import com.sicknasty.objects.Exceptions.UserCreationException;

import java.util.ArrayList;


//@project sicknasty
///@author aaron salo
//the user object manages the each individual user account. Stores a password object, and other
//things related to the user
public class User {
    private String name; //the "display name" of the user
    private String userName; //used for storage, searching, etc.
    private String password; //just store unsecure pass for now

    //ArrayList<User> followers; //list of people that follow the user
    private ArrayList<User> follows; //list of people that the user follows

    private PersonalPage personalPage; //the users personal page *****WAITING FOR PAGE IMPLEMENTATON****

    public User(String name, String username, String password)throws PasswordErrorException, UserCreationException,
            ChangeNameException, ChangeUsernameException {
        changeName(name);
        changeUsername(username);
        changePassword(password);
        personalPage = new PersonalPage(this); //create a personal page for this user
        follows = new ArrayList<>();
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
        newName = newName.trim(); //trim the whitespace
        if(newName.length() > Constants.USER_MIN_USERNAME_LENGTH) {
            if (newName.length() < Constants.USER_MAX_USERNAME_LENGTH) {
                name = newName;
                success = true;
            } else
                throw new ChangeNameException("The username was too long; must be shorter than " + Constants.USER_MAX_USERNAME_LENGTH);
        } else
            throw new ChangeNameException(("The username was too short; must be at least " + Constants.USER_MIN_USERNAME_LENGTH + " characters"));
        return success;
    }

    public String getUsername(){return userName;}

    /**
     * Pass a new password through a hashing function
     * @return  true on success
     */
    public void changePassword(String input) throws PasswordErrorException{
        input = input.trim(); //get the whitespace off the ends
        if(input != null) {
            if (!input.contains(" ")) {
                int passwordLength = input.length();

                if (passwordLength >= Constants.USER_MIN_PASS_LENGTH && passwordLength <= Constants.USER_MAX_PASS_LENGTH) {
                    password = input;
                } else {
                    throw new PasswordErrorException("The password length must be between " + Constants.USER_MIN_PASS_LENGTH + " and " + Constants.USER_MAX_PASS_LENGTH);
                }
            } else
                throw new PasswordErrorException("Password cannot contain spaces");
        } else //this should never occur
            throw new PasswordErrorException("Some unknown error occurred with password creation, please try again");
    }//end of change password

    public boolean checkPasswordCorrect(String inputPass){
        return password.equals(inputPass); //check if the password is correct
    }

    public ArrayList<User> getFollows() {
        return follows;
    }

    public ArrayList<User> getFollowersList(){
        return personalPage.getFollowers();
    }

    public int getPostsSize(){
        return personalPage.getPostList().size();
    }

    public String getPassword() {
        return this.password;
    }

    /**change the user name
     * there is some error checking to see if the username isn't too long, or isn't used by anyone
     * etc. If the checks fail, it will return false and do nothing, otherwise, change the username
     * @return true on success
     */
    public void changeUsername(String newUsername) throws ChangeUsernameException{
        //check if newUsername is used by anyone
        newUsername = newUsername.trim(); //trim whitespace off edges

        if (newUsername!=null) {
            if ((newUsername.length() <= Constants.USER_MAX_USERNAME_LENGTH) && (newUsername.length() >= Constants.USER_MIN_USERNAME_LENGTH)) { //is the newUsername an appropriate length
                if (!newUsername.contains(" ")) { //check to see if the string contains whitespace
                    this.userName = newUsername;
                } else {
                    throw new ChangeUsernameException("Username cannot contain whitespace");
                }
            } else {
                throw new ChangeUsernameException("Username must be longer than " + Constants.USER_MIN_USERNAME_LENGTH + " and shorter than " + Constants.USER_MAX_USERNAME_LENGTH);
            }
        } else //this should never occur... but... just in case
            throw new ChangeUsernameException("An unknown error occured when trying to update the username");
    } //changeUsername

    //add a new user to the list of users you follow
    public void follow(User newFollow) {
        follows.add(newFollow);
    }//follow

} //end of class

