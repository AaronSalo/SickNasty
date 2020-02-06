package com.sicknasty;

import com.sicknasty.business.AccessPostsTest;
import com.sicknasty.business.AccessUsersTest;
import com.sicknasty.objects.*;

import org.junit.Test;


/**
 * @author aaron salo
 * this class simply calls all tests from every test class
 */
public class AllTests {

    @Test
    public void callAllTests() {
        callObjectTests();
        callBusinessTests();
    }


    /**
     * method for calling all the object tests
     */
    @Test
    public void callObjectTests() {
        UserTest userTester = new UserTest();
        PasswordTest passTester = new PasswordTest();

        userTester.allUserTests();
        passTester.allPasswordTests();
    }


    /**
     * method for calling all the business class tests
     */
    @Test
    public void callBusinessTests() {
        AccessUsersTest accessUsersTest = new AccessUsersTest();
        AccessPostsTest accessPostsTest = new AccessPostsTest();

        //accessUsersTest.allAccessUsersTests(); //get jay to implement this
        accessPostsTest.allAccessPostTests();
    }
}
