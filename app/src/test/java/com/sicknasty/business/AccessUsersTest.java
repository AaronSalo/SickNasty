package com.sicknasty.business;

import com.sicknasty.application.Service;
import com.sicknasty.objects.User;
import com.sicknasty.persistence.UserPersistence;

import junit.framework.TestCase;

import org.junit.Test;
import static org.junit.Assert.*;

public class AccessUsersTest {

    AccessUsers users=new AccessUsers();              //use business layer
    @Test
    public void testInsertUsers() {

        assertNotNull(users.insertUser("Jay K","jay","23416772"));
        assertNotNull("user not found in the database",users.validNewUsername("jay"));
        assertTrue("wrong password found for jay",users.validNewUsername("jay").checkPasswordCorrect("23416772"));

        assertNotNull(new User("Aaron Solo","aaron","abcdefg"));


        assertNotNull(users.insertUser("Aaron Solo","aaron","abcdefg"));
        assertNull("user not created found in the database",users.validNewUsername("aaron1"));

        assertTrue("user not deleted",users.deleteUser("jay"));
        assertTrue("user not deleted",users.deleteUser("aaron"));

    }
    @Test
    public void testDuplicateUsers(){
        assertNotNull("user not added",users.insertUser("Jay K","jay","abcmmdef"));
        assertNull("duplicated  added!!!Error",users.insertUser("Jay K","jay","abcmmdef"));


        assertFalse("item not found but still deleted!!error",users.deleteUser("aaron"));
        assertTrue("existing user not deleted !!error",users.deleteUser("jay"));
    }
    @Test
    public void testUpdatesInUsername(){
        User user1=users.insertUser("Jay K","jay","abcmmdef");

        assertNotNull("user not added",user1);
        assertTrue("username not changed even though it was available",users.updateUsername(user1,"aaron"));

        assertEquals("nd nad",user1.getUsername(),"aaron");
        assertNotNull("user not added",users.insertUser("Jay K","jay","abcmmdef"));
        assertNull("duplicated  added!!!Error",users.insertUser("Jay K","jay","abcmmdef"));

        assertTrue(" not deleted!!error",users.deleteUser("aaron"));
        assertTrue("existing user not deleted !!error",users.deleteUser("jay"));
    }

    @Test
    public void testUpdatesInPassword(){
        User jay=users.insertUser("Jay K","jay","abcmmdef");

        assertNotNull("user not added",jay);
        assertTrue("username not changed even though it was available",users.updateUserPassword("jay","abcmmdef","234567819"));

        assertFalse("password not change",jay.checkPasswordCorrect("abcmmdef"));
        assertTrue("password not change",jay.checkPasswordCorrect("234567819"));


        assertTrue(" not deleted!!error",users.deleteUser("jay"));

    }


}
