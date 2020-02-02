package com.sicknasty.objects;

import java.util.ArrayList;

public class User {

    private static int globalUserID = 0; //global int, increments every time a new user is created
    private int userID;

    private String userName;
    private Password password;

    ArrayList<User> followers; //list of people that follow the user
    ArrayList<User> follows; //list of people that the user follows

    //PersonalPage personalPage; //the users personal page *****WAITING FOR PAGE IMPLEMENTATON****

    private int maxUsernameLength = 12; //username cannot be longer than 12 characters

    public User(String userName, String password) {
        this.userID = globalUserID;
        globalUserID++;
        this.userName =  userName;
        this.password = new Password(password);
        //personalPage = new PersonalPage(); //*******WAITING FOR PAGE IMPLEMENTATION********
    }//end of constructor


    public String getUsername(){return userName;}

    public int getUserID() {return userID;}

    //Pass a new password through a hashing funciton
    public void changePassword(String newPass) {
        this.password.changePassword(newPass);
    }//end of change password


    //change the user name
    //there is some error checking to see if the username isn't too long, or isn't used by anyone
    //etc. If the checks fail, it will return false and do nothing, otherwise, change the username
    //and return true
    public boolean changeUsername(String newUsername) {
        //check if newUsername is used by anyone
        if(true) { //to-do check other usernames
            if(newUsername.length() < maxUsernameLength) { //is the newUsername short enough
                if(newUsername.contains(" ")) { //check to see if the string contains whitespace
                    userName = newUsername;
                    return true;
                }//if
            }//if
        }//if
        return false;
    } //changeUsername

    //if you need an explanation for this one.... idk man....
    public void addFollower(User newFollower) {
        followers.add(newFollower);
    }//addFollower


    //add a new user to the list of users you follow
    public void follow(User newFollow) {
        follows.add(newFollow);
    }//follow

} //end of class
