package com.sicknasty;

import com.sicknasty.objects.*;

import org.junit.Test;


/**
 * @author aaron salo
 * this class simply calls all tests from every test class
 */
public class AllTests {

    @Test
    public void callAllTests() {
        UserTest userTester = new UserTest();
        PasswordTest passTester = new PasswordTest();

        userTester.allUserTests();
        passTester.allPasswordTests();
    }
}
