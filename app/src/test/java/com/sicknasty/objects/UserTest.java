package com.sicknasty.objects;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {

    /**
     * this just calls all the test based around the user class
     */
    @Test
    public void allUserTests() {
        testPassChange();
        testUsernameChange();
    }

    /**
     * check that password changing works
     * creates a user, tries changing the password, and making sure we can correctly log in with
     * the new password and not the old one
     */
    @Test
    public void testPassChange() {
        String oldPass = "thisisabadpass1";
        String newPass = "thisisasecurepass2";
        User user1 = new User("whatevre","Mr. BOB", oldPass);
        assertTrue(user1.changePassword(newPass));//change to a new password
        assertFalse(user1.checkPasswordCorrect(oldPass)); //the old password should NOT work
        assertTrue(user1.checkPasswordCorrect(newPass)); //the new password should work fine
    }

    /**
     * check if the username can be properly changed
     */
    @Test
    public void testUsernameChange() {
        User user = new User("vmsmadvvmadmh","username", "123");
        assertTrue(user.changeUsername("newUsername"));
    }


    @Test
    public void addFollower() {
    }

    @Test
    public void follow(){   //case for checking if you follow yourself, you get added to your followers list
                            //doesn't work. could make it so we don't allow the user to follow themself as a test

        User user1 = new User("bdamd damm","Mr. BOBob", "1234");

        user1.follow(user1);

    }
}