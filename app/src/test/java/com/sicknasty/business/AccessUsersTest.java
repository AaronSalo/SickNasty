package com.sicknasty.business;

import com.sicknasty.application.Service;
import com.sicknasty.objects.User;
import com.sicknasty.persistence.UserPersistence;

import junit.framework.TestCase;

import org.junit.Test;
import static org.junit.Assert.*;

public class AccessUsersTest {

    UserPersistence userData= Service.getUserData();
    @Test
    public void testNullUser(){
        User user1=new User("Jay",null,null);
        assertNull(userData.insertNewUser(user1));


        user1=new User("jay","jay",null);
        assertNull(userData.insertNewUser(user1));

        user1=new User("jay",null,"abcd");
        assertNull(userData.insertNewUser(user1));
    }
    @Test
    public void testInsertUsers() {
        User user1=new User("Jay K","jay","abcdeff");;
        User user2;
        assertNotNull(userData.insertNewUser(user1));
        assertTrue("user not found in the database",userData.getUser(user1.getUsername())!=null);
        assertEquals(userData.getUser(user1.getUsername()).getUsername(),"jay");
        assertTrue("password not saved correctly",userData.getUser(user1.getUsername()).checkPasswordCorrect("2345"));

        user2=new User("Aaron Solo","aaron","abcdefg");;
        assertNotNull(userData.insertNewUser(user2));
        assertTrue("cannot find aaron",userData.getUser(user1.getUsername())!=null);
        assertEquals("username doesn't match with aaron",userData.getUser(user1.getUsername()).getUsername(),"aaron");
        assertEquals(userData.getUser(user1.getUsername()).checkPasswordCorrect("1234"),"true");

        assertTrue("user not deleted",userData.deleteUser(user1));
        assertTrue("user not deleted",userData.deleteUser(user2));

    }
    @Test
    public void testDuplicateUsers(){

        User user1=new User("Jay K","jay","abcdef");

        assertNotNull("user not added successfully",userData.insertNewUser(user1));
        assertNull("duplicate added",userData.insertNewUser(user1));

        assertTrue("",userData.getUser(user1.getUsername()).checkPasswordCorrect("abcdef"));
    }
    @Test
    public void testNotNullUser(){
        User user1=new User("axhbabha","jay","abcd"),user2;
        assertNotNull(userData.insertNewUser(user1));

        user2=new User("jay",null,"abcd");
        assertNull(userData.insertNewUser(user1));
        assertTrue("user not deleted",userData.deleteUser(user1));

        assertFalse("user with null username added",userData.insertNewUser(user2)!=null);

        assertFalse("user not found",userData.deleteUser(user2));
    }
    @Test
    public void testDelete(){
        User jay=new User("Jay K","jay","abcdef");
        User aaron=new User("Aaron Solo","aaron","abcdef");

        assertNotNull(userData.insertNewUser(jay));
        assertNotNull(userData.insertNewUser(aaron));

        assertTrue("user not deleted",userData.deleteUser(jay));
        assertTrue("user not deleted",userData.deleteUser(aaron));

        assertFalse("user not found",userData.deleteUser(jay));
        assertFalse("user not found",userData.deleteUser(aaron));


    }


}
