package com.sicknasty.objects;

import org.junit.Test;

import static org.junit.Assert.*;

public class PasswordTest {

    @Test
    public void changePassword() {
    }

    @Test
    public void checkPass() {       //checking to see if passwords are equal after going through hash
                                    // and salt when first creating them and when checking them

        Password pass = new Password("ILikeTurtles123");
        assertTrue("checking failed. not the right password ",pass.checkPass("ILikeTurtles123"));

    }
}