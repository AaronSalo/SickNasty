package com.sicknasty.objects;

//PROJECT: sick nasty
//this class is meant to manage the pages, and users. The pages themselves will manage the posts
//--note-- this class is a rough draft of its final implementation. Will need refactoring, and
//rewriting after/during discussion with other group members

//@author aaron salo
public class Manager {

    User myUser; //the user currently logged in
    //Page currentPage; // the current page we are working on

    public Manager() {
        if(myUser == null){
            //login
            //prompt user for username and password
            //query db to see if we can find the user, and if the password matches
            //if successful login
                //myUser = user
            //or signup; same thing but rather than searching, create a new user
        }
    }

   public void getPage(String pagename) {
       //search for page with the given pagename, get the associated page
   }//getPage

   public User getUser(String username) {
        //search for user with the given username and return the user account associated

        return null;
   }//getUser

}//end of class
