package com.sicknasty.business;

import com.sicknasty.objects.Exceptions.ChangeNameException;
import com.sicknasty.objects.Exceptions.ChangeUsernameException;
import com.sicknasty.objects.Exceptions.PasswordErrorException;
import com.sicknasty.objects.Exceptions.UserCreationException;
import com.sicknasty.objects.Exceptions.UserNotFoundException;
import com.sicknasty.objects.User;
import com.sicknasty.persistence.UserPersistence;
import com.sicknasty.persistence.exceptions.DBUsernameExistsException;
import com.sicknasty.persistence.exceptions.DBUsernameNotFoundException;
import com.sicknasty.persistence.stubs.UserPersistenceStub;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class AccessUsersTest {

    private UserPersistence userPersistence=new UserPersistenceStub();
    private AccessUsers users = new AccessUsers(userPersistence);
    @Test
    public void testInsertUsers() throws ChangeNameException, PasswordErrorException, UserCreationException, ChangeUsernameException, DBUsernameExistsException, DBUsernameNotFoundException, UserNotFoundException {

        User newUser=new User("Jay K","jay1","1234567");
        assertNotNull(users.insertUser(newUser));

        assertNotNull(users.insertUser(new User("Aaron Solo","aaron","abcdefg")));

        users.deleteUser(users.getUser("jay1").getUsername());
        users.deleteUser(users.getUser("aaron").getUsername());

    }
    @Test(expected = DBUsernameExistsException.class)
    public void testDuplicateUsers() throws ChangeNameException, PasswordErrorException, UserCreationException, ChangeUsernameException, DBUsernameExistsException, DBUsernameNotFoundException, UserNotFoundException {
        User newUser=new User("Jay K","jay1","1234567");
        assertNotNull(users.insertUser(newUser));
        assertNull("duplicated  added!!!Error",users.insertUser(new User("Jay K","jay1","abcmmdef")));

        users.deleteUser(users.getUser("aaron").getUsername());
        users.deleteUser(users.getUser("jay").getUsername());
    }
    @Test(expected = DBUsernameExistsException.class)
    public void testUpdatesInUsername() throws DBUsernameNotFoundException, ChangeNameException, PasswordErrorException, UserCreationException, ChangeUsernameException, DBUsernameExistsException, UserNotFoundException {
        User user1=new User("Jay K","jay","abcmmdef");


        assertNotNull("user not added",users.insertUser(user1));
        users.updateUsername(user1,"aaron");

        assertNotNull("nd nad",users.getUser("aaron"));
        assertNotNull("user not added",users.insertUser(user1));
        assertNull("duplicated  added!!!Error",users.insertUser(new User("Jay K","jay","abcmmdef")));

        users.deleteUser(users.getUser("aaron").getUsername());
        users.deleteUser(users.getUser("jay").getUsername());
    }

    @Test
    public void testUpdatesInPassword() throws Exception {
        User jay=new User("Jay K","jay","abcmmdef");

        assertNotNull("user not added",users.insertUser(jay));

        users.updateUserPassword("jay","1234556777");
        assertFalse("password not change",jay.checkPasswordCorrect("abcmmdef"));
        assertTrue("password not changed",jay.checkPasswordCorrect("1234556777"));

        users.deleteUser("jay");
    }

    @Test
    public void testAllUsers() throws Exception {
        User jay = new User("Jay K","jay","abcmmdef");
        User aaron =  new User("Aaron Salo","aaron","abcmmdef");

        assertNotNull("user not added",users.insertUser(jay));
        assertNotNull("user not added",users.insertUser(aaron));

        ArrayList<String> list = users.getUsersByUsername();
        assertTrue("user jay was added but not found",list.contains("jay"));
        assertTrue("user aaron was added but not found",list.contains("aaron"));
        assertFalse("user not added but still found",list.contains("jay1"));

        users.deleteUser("jay");
        users.deleteUser("aaron");
    }

}
