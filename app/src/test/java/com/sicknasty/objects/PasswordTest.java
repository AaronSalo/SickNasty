package com.sicknasty.objects;

import org.junit.Test;

import static org.junit.Assert.*;

public class PasswordTest {

    @Test
    public void allPasswordTests() {
        changePassword();
        checkPass();
    }

    @Test
    public void changePassword() {
        Password pass = new Password("oldPassword1"); //create a pass
        assertTrue("Changing the password didn't work", pass.changePassword("newPassword")); //change it
        assertFalse("the old password worked for some reason", pass.checkPass("oldPassword")); //old pass shouldnt work
        assertTrue("the new password didn't work after changing the password", pass.checkPass("newPassword")); //new on should work
    }

    @Test
    public void checkPass() {       //checking to see if passwords are equal after going through hash
                                    // and salt when first creating them and when checking them

        Password pass = new Password("ILikeTurtles123");
        assertTrue("checking failed. not the right password ",pass.checkPass("ILikeTurtles123"));

    }
}