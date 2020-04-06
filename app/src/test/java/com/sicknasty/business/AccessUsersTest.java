package com.sicknasty.business;

import com.sicknasty.objects.Exceptions.ChangeNameException;
import com.sicknasty.objects.Exceptions.ChangeUsernameException;
import com.sicknasty.objects.Exceptions.MessageException;
import com.sicknasty.objects.Exceptions.PasswordErrorException;
import com.sicknasty.objects.Exceptions.UserCreationException;
import com.sicknasty.objects.Exceptions.UserNotFoundException;
import com.sicknasty.objects.Message;
import com.sicknasty.objects.User;
import com.sicknasty.persistence.UserPersistence;
import com.sicknasty.persistence.exceptions.DBUsernameExistsException;
import com.sicknasty.persistence.exceptions.DBUsernameNotFoundException;
import com.sicknasty.stubs.UserPersistenceStub;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AccessUsersTest {

    private AccessUsers accessUsersStub;
    private AccessUsers accessUsersMock;
    private UserPersistence userPersistenceMock,userPersistenceStub;

    @Before
    public final void setup()
    {
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
        //use mockito
        User jay = new User("Jay K","jay","abcmmdef");
        User aaron =  new User("Aaron Salo","aaron","abcmmdef");

        when(userPersistenceMock.insertNewUser(any(User.class))).thenAnswer(new Answer<User>()
        {
            @Override
            public User answer(InvocationOnMock invocation) throws Throwable
            {
                Object[] args = invocation.getArguments();
                return (User) args[0];
            }
        });

        accessUsersMock.insertUser(jay);
        accessUsersMock.insertUser(aaron);

        //make sure we insert it
        when(userPersistenceMock.getUser("jay")).thenReturn(jay);
        when(userPersistenceMock.getUser("aaron")).thenReturn(aaron);


        final ArrayList<String> users = new ArrayList<>();
        users.add(jay.getUsername());
        users.add(aaron.getUsername());

        when(userPersistenceMock.getAllUsers()).thenReturn(users);
        assertEquals(accessUsersMock.getUsersByUsername(), users);

        //verify all method calls
        verify(userPersistenceMock).insertNewUser(jay);
        verify(userPersistenceMock).insertNewUser(aaron);
    }

    @Test
    public void testMessageFields() throws DBUsernameExistsException, ChangeNameException, PasswordErrorException, UserCreationException, ChangeUsernameException, MessageException, DBUsernameNotFoundException
    {
        String username =  "user1";
        String username1 = "user2";
        User user1 = new User("user 1", username, "password");
        User user2 = new User("user 2", username1, "password");

        when(userPersistenceMock.insertNewUser(user1)).thenReturn(user1);
        when(userPersistenceMock.insertNewUser(user2)).thenReturn(user2);

        //this is just to make sure at the end of test that we have called the function
        accessUsersMock.insertUser(user1);
        accessUsersMock.insertUser(user2);

        when(userPersistenceMock.getUser(username)).thenReturn(user1);
        when(accessUsersMock.getUser(username1)).thenReturn(user2);

        Message msg = new Message("hello good sir *tips fedora*", user1, user2);
        Message msg1 = new Message("hello back to you mr user1", user2, user1);
        Message msg2 = new Message("jemepelle user1", user1, user2);

        accessUsersMock.addMessage(msg);
        accessUsersMock.addMessage(msg1);
        accessUsersMock.addMessage(msg2);

        ArrayList<Message> messages = userPersistenceMock.getMessages(user1,user2);
        messages.add(msg);
        messages.add(msg1);
        messages.add(msg2);

        doReturn(messages).when(userPersistenceMock).getMessages(user1,user2);
        assertEquals(messages,accessUsersMock.getMessages(user1,user2));

        //make verify that all the functions are called-->verify(insert)
        verify(userPersistenceMock).insertNewUser(user1);
        verify(userPersistenceMock).insertNewUser(user2);
        verify(userPersistenceMock).addMessage(msg);
        verify(userPersistenceMock).addMessage(msg1);
        verify(userPersistenceMock).addMessage(msg2);
    }

    @Test
    public void testDifferentUsersMessage() throws ChangeNameException, PasswordErrorException, UserCreationException, ChangeUsernameException, DBUsernameExistsException, MessageException
    {

        String username =  "user1";
        String username1 = "user2";
        String username2 = "user3";
        User user1 = new User("user 1", username, "password");
        User user2 = new User("user 2", username1, "password");
        User user3 = new User("user 2", username2, "password");

        doReturn(user1).when(userPersistenceMock).insertNewUser(user1);
        doReturn(user1).when(userPersistenceMock).insertNewUser(user2);
        doReturn(user1).when(userPersistenceMock).insertNewUser(user3);

        //this is just to make sure at the end of test that we have called the function
        accessUsersMock.insertUser(user1);
        accessUsersMock.insertUser(user2);
        accessUsersMock.insertUser(user3);

        Message msg = new Message("hello good sir *tips fedora*", user1, user2);
        Message msg1 = new Message("hello back to you mr user1", user3, user1);
        Message msg2 = new Message("hello good sir *tips fedora*", user1, user3);
        Message msg3 = new Message("hello good sir *tips fedora*", user2, user3);


        accessUsersMock.addMessage(msg);
        accessUsersMock.addMessage(msg1);
        accessUsersMock.addMessage(msg2);
        accessUsersMock.addMessage(msg3);


        ArrayList<Message> messageArrayList = new ArrayList<>();
        messageArrayList.add(msg);
        when(userPersistenceMock.getMessages(user1,user2)).thenReturn(messageArrayList);

        assertEquals(messageArrayList,accessUsersMock.getMessages(user1,user2));

        messageArrayList = new ArrayList<>();
        messageArrayList.add(msg1);
        messageArrayList.add(msg2);

        when(userPersistenceMock.getMessages(user1,user3)).thenReturn(messageArrayList);
        assertEquals(messageArrayList, accessUsersMock.getMessages(user1,user3));
        messageArrayList = new ArrayList<>();
        messageArrayList.add(msg3);

        //make sure that you are
        doReturn(messageArrayList).when(userPersistenceMock).getMessages(user2,user3);
        doReturn(messageArrayList).when(userPersistenceMock).getMessages(user3,user2);
        assertEquals(messageArrayList, accessUsersMock.getMessages(user2,user3));



        //check messages between to make sure that is it is going to right user
        //still verify all the calls
        verify(userPersistenceMock).insertNewUser(user1);
        verify(userPersistenceMock).insertNewUser(user2);
        verify(userPersistenceMock).insertNewUser(user3);
        verify(userPersistenceMock).addMessage(msg);
        verify(userPersistenceMock).addMessage(msg1);
        verify(userPersistenceMock).addMessage(msg2);
        verify(userPersistenceMock).addMessage(msg3);
    }

    @Test(expected = MessageException.class)
    public void testInsertValidMessages() throws ChangeNameException, PasswordErrorException, UserCreationException, ChangeUsernameException, DBUsernameExistsException, DBUsernameNotFoundException, UserNotFoundException, MessageException {

        String username = "user1";
        String username1 = "user2";
        User user1 = new User("user 1", username, "password");
        User user2 = new User("user 2", username1, "password");

        when(userPersistenceMock.insertNewUser(user1)).thenReturn(user1);
        when(userPersistenceMock.insertNewUser(user2)).thenReturn(user2);


        //this is just to make sure at the end of test that we have called the function
        accessUsersMock.insertUser(user1);
        accessUsersMock.insertUser(user2);

        when(userPersistenceMock.getUser(username)).thenReturn(user1);
        when(accessUsersMock.getUser(username1)).thenReturn(user2);

        Message msg = new Message("", user1, user2);
        doThrow(MessageException.class).when(userPersistenceMock).addMessage(msg);

        msg = new Message("sjnjknfkjesktbkjerdkjgjdngjdkjfngk" +
                "jbdfkjgbkjdbgkjbdkjxbcvkjdfbkbksdbkvbdskfbvkbedkfbgkerbdfkgb" +
                "kdbvkdbfkvbrkdbklebdkvbkdbflkgkdbvkdbkbvdkbvkbvkbfkjgjfhkfngkd" +
                "bfgbskgverkhgiuvhsgiersdlgdlvdblgsbdfgbcerldgbidbfgkbcludfbgiulcd" +
                "fuigildkjbg", user2, user1);
        doThrow(MessageException.class).when(userPersistenceMock).addMessage(msg);

        //verify all the method calls
        verify(userPersistenceMock).insertNewUser(user1);
        verify(userPersistenceMock).insertNewUser(user2);
        verify(userPersistenceMock,times(2)).addMessage(msg);
    }

    @After
    public final void tearDown()
    {
        userPersistenceMock = null;
        userPersistenceStub = null;

        accessUsersStub = null;
        accessUsersMock = null;
    }
}
