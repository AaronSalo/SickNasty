package com.sicknasty.objects;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {

    @Test
    public void changePassword() {  //checks to see that changing password is

        User user1 = new User("Mr. BOB", "1234");





    }

    @Test
    public void addFollower() {
    }

    @Test
    public void follow(){   //case for checking if you follow yourself, you get added to your followers list
                            //doesn't work. could make it so we don't allow the user to follow themself as a test

        User user1 = new User("Mr. BOBob", "1234");

        user1.follow(user1);



    }
}