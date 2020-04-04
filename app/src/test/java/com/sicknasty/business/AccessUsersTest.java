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
import com.sicknasty.stubs.UserPersistenceStub;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class AccessUsersTest {

    private AccessUsers accessUsersStub;
    private AccessUsers accessUsersMock;


    @Before
    public final void setup()
    {
        UserPersistence userPersistenceMock,userPersistenceStub;

        userPersistenceStub = new UserPersistenceStub();
        userPersistenceMock = mock(UserPersistence.class);

        accessUsersStub = new AccessUsers(userPersistenceStub);
        accessUsersMock = new AccessUsers(userPersistenceMock);
    }
    @Test
    public void testInsertUsers() throws ChangeNameException, PasswordErrorException, UserCreationException, ChangeUsernameException, DBUsernameExistsException, DBUsernameNotFoundException, UserNotFoundException {

        User newUser=new User("Jay K","jay1","1234567");
        assertNotNull(accessUsersStub.insertUser(newUser));

        assertNotNull(accessUsersStub.insertUser(new User("Aaron Solo","aaron","abcdefg")));
    }

    @Test(expected = DBUsernameExistsException.class)
    public void testDuplicateUsers() throws ChangeNameException, PasswordErrorException, UserCreationException, ChangeUsernameException, DBUsernameExistsException {

        User newUser=new User("Jay K","jay1","1234567");
        assertNotNull(accessUsersStub.insertUser(newUser));
        assertNull("duplicated  added!!!Error",accessUsersStub.insertUser(new User("Jay K","jay1","abcmmdef")));

    }

    @Test(expected = DBUsernameExistsException.class)
    public void testUpdatesInUsername() throws DBUsernameNotFoundException, ChangeNameException, PasswordErrorException, UserCreationException, ChangeUsernameException, DBUsernameExistsException, UserNotFoundException {
        User user1=new User("Jay K","jay","abcmmdef");


        assertNotNull("user not added",accessUsersStub.insertUser(user1));
        accessUsersStub.updateUsername(user1,"aaron");

        assertNotNull("nd nad",accessUsersStub.getUser("aaron"));
        assertNotNull("user not added",accessUsersStub.insertUser(user1));
        assertNull("duplicated  added!!!Error",accessUsersStub.insertUser(new User("Jay K","jay","abcmmdef")));
    }

    @Test
    public void testUpdatesInPassword() throws Exception
    {
        User jay=new User("Jay K","jay","abcmmdef");

        assertNotNull("user not added",accessUsersStub.insertUser(jay));

        accessUsersStub.updateUserPassword("jay","1234556777");
        assertFalse("password not change",jay.checkPasswordCorrect("abcmmdef"));
        assertTrue("password not changed",jay.checkPasswordCorrect("1234556777"));
    }


    @Test
    public void testAllUsers() throws Exception
    {
        User jay = new User("Jay K","jay","abcmmdef");
        User aaron =  new User("Aaron Salo","aaron","abcmmdef");

        assertNotNull("user not added",accessUsersStub.insertUser(jay));
        assertNotNull("user not added",accessUsersStub.insertUser(aaron));

        ArrayList<String> list = accessUsersStub.getUsersByUsername();
        assertTrue("user jay was added but not found",list.contains("jay"));
        assertTrue("user aaron was added but not found",list.contains("aaron"));
        assertFalse("user not added but still found",list.contains("jay1"));
    }

    @Test
    public void testDeleteUser() throws ChangeNameException, PasswordErrorException, UserCreationException, ChangeUsernameException, DBUsernameExistsException {

        //use mockito
        User jay = new User("Jay K","jay","abcmmdef");

        assertNotNull("user not added",accessUsersStub.insertUser(jay));


    }
    @Test
    public void testInsertedUserFields() throws ChangeNameException, PasswordErrorException, UserCreationException, ChangeUsernameException, DBUsernameExistsException
    {
        //use mockito
        User jay = new User("Jay K","jay","abcmmdef");
        assertNotNull("user not added",accessUsersStub.insertUser(jay));


    }

    //message tests in mockito


    @After
    public final void tearDown()
    {
        accessUsersStub = null;
        accessUsersMock = null;
    }


}
