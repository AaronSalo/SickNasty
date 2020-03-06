package com.sicknasty.business;

import com.sicknasty.application.Service;
import com.sicknasty.objects.Exceptions.UserNotFoundException;
import com.sicknasty.objects.User;
import com.sicknasty.persistence.UserPersistence;
import com.sicknasty.persistence.exceptions.DBUsernameExistsException;
import com.sicknasty.persistence.exceptions.DBUsernameNotFoundException;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AccessUsersIT {

    AccessUsers users;

    @Before public void setUp() {
        Service.initTestDatabase();
        users = new AccessUsers();              //use business layer
    }

    @Test
    public void testInsertUsers() {

        try {
            User newUser = new User("Jay K", "jay", "23416772");
            users.insertUser(newUser);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail();
        }
    }

    @Test(expected = DBUsernameNotFoundException.class)
    public void testDeleteUsers() throws DBUsernameNotFoundException{
        String username = "jay";
        try {
            User newUser = new User("Jay K", username, "23416772");
            users.insertUser(newUser);
            users.deleteUser(username);
        } catch (Exception e) {
            fail();
        }

        try {
            users.getUser(username); //should throw an exception
        } catch (DBUsernameNotFoundException e) {
            throw e;
        } catch (UserNotFoundException e) {
            fail();
        }
    }

    @Test(expected = DBUsernameExistsException.class)
    public void testUsernameExistsException() throws DBUsernameExistsException {

        try {
            User newUser = new User("Jay K", "jay", "23416772");
            User sameUser = new User("Jay K", "jay", "23416772");
            users.insertUser(newUser);
            users.insertUser(sameUser);
        } catch (DBUsernameExistsException e) {
            throw new DBUsernameExistsException("Username already Exists");
        } catch (Exception e) {
            fail();
        }
    }


    @Test
    public void testUpdatesInUsername(){
        String username = "jay";
        String newUsername = "aaron";
        try {
            User user1 = new User("Jay K",username,"abcmmdef");
            users.insertUser(user1);
            users.updateUsername(user1, newUsername);
            user1 = users.getUser(username);
            assert(user1.getName() == username);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail();
        }
    }

    @Test
    public void testUpdatesInPassword(){
        try {
            User jay = new User("Jay K", "jay", "abcmmdef");
            users.insertUser(jay);

            users.updateUserPassword("jay",  "234567819");

            assertFalse("password is still the old password", jay.checkPasswordCorrect("abcmmdef"));
            assertTrue("the new password didnt work", jay.checkPasswordCorrect("234567819"));
        } catch (Exception e) {
            fail();
        }
    }


}
