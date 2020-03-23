package com.sicknasty.objects;

import com.sicknasty.objects.Exceptions.ChangeNameException;
import com.sicknasty.objects.Exceptions.ChangeUsernameException;
import com.sicknasty.objects.Exceptions.PasswordErrorException;
import com.sicknasty.objects.Exceptions.UserCreationException;
import com.sicknasty.objects.Exceptions.UserNotFoundException;

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


    @Test(expected = PasswordErrorException.class)
    public void passTooShortTest() throws PasswordErrorException{
        String longPass = "";
        for(int i = 0; i < User.MIN_PASS_LENGTH+1; i++) {
            longPass+= "f";
        }

        String shortPass = "short";
        assert(shortPass.length() < User.MIN_PASS_LENGTH);

        try {
            User goodUser = new User("thisIsAName", "userName", longPass);
            User badUser = new User( "thisName", "another", shortPass);
            fail(); //if we get here; fail the test
        } catch (PasswordErrorException e) {
            System.out.println("yello");
            throw new PasswordErrorException("Somethign went wrong");
        } catch (ChangeUsernameException | UserCreationException | ChangeNameException e){
            e.printStackTrace();
        }
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
        try {
            User user1 = new User("whatevre", "Mr. BOB", oldPass);
            user1.changePassword(newPass);//change to a new password
            assertFalse(user1.checkPasswordCorrect(oldPass)); //the old password should NOT work
            assertTrue(user1.checkPasswordCorrect(newPass)); //the new password should work fine
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail();
        }
    }

    /**
     * check if the username can be properly changed
     */
    @Test
    public void testUsernameChange() {
        try {
            String oldUsername = "username";
            String newUsername = "newUsername";
            User user = new User("vmsmadvvmadmh", oldUsername, "123");
            user.changeUsername(newUsername);
            assert (user.getUsername() != oldUsername);
            assert (user.getUsername() == newUsername);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail();
        }
    }

    @Test
    public void testCorrectReceiver(){
        try{
            User user1 = new User("bobbington", "Mr. BOB", "password");
            User user2 = new User("bobbington", "Mr. NOTBOB", "password");
            User user3 = new User("bobbington", "Mr. NOTNOTBOB", "password");

            Message msg = new Message("hey bud, hows it goin",user1,user2);

            assertEquals(msg.getReceiver(),user2);
            assertFalse(msg.getReceiver().getName().equals(user3.getName()));
        }catch (Exception e){
            fail();
        }
    }




    @Test
    public void testMessageIncorrectLength(){           //testing to long message and no character message when initially making them
        try{
            User user1 = new User("bobbington", "Mr. BOB", "password");
            User user2 = new User("bobbington", "Mr. NOTBOB", "password");
            Message msg = new Message("",user1,user2);
            Message msg1 = new Message("1234567890123456789212345678931234567890423598345982357" +
                    "982395823985982379823938923873498237438934289982398472398749827947923798472897498" +
                    "793749329439832977429739793243095809348509834095098080328098dsjhkjdbbkjsdbdkjdskjdnn" +
                    "jksdnfnsd,fn,dsnfnksdknskdnfkjnsd3fkjsd3fjkjdsfkjhsdkdhfkjdshkfhsk3dhfkjhs3jfdhkjsdh432",user1,user2);

        }catch (Exception e){           //should be catching message exception
            fail();
        }

    }


}