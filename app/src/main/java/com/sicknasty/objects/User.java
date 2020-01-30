
import java.util.ArrayList;

public class User {

    private String userName;
    private String passwordHASHED; //MAKE SURE we store a hashed version

    ArrayList<User> followers; //list of people that follow the user
    ArrayList<User> follows; //list of people that the user follows

    //PersonalPage personalPage; //the users personal page *****WAITING FOR PAGE IMPLEMENTATON****

    private int maxUsernameLength = 12; //username cannot be longer than 12 characters

    public User(String userName, String passwordHASHED) {
        this.userName =  userName;
        this.passwordHASHED = passwordHASHED;
        //personalPage = new PersonalPage(); //*******WAITING FOR PAGE IMPLEMENTATION********
    }//end of constructor


    //Pass a new password through a hashing funciton
    public void changePassword(String newPass) {
        //pass thru hash function, not available yet
        String newPassHASHED = newPass; //just for now, waiting for jays hash function
        passwordHASHED = newPassHASHED;
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
