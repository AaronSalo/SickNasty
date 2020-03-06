package com.sicknasty.objects;

import com.sicknasty.objects.Exceptions.ChangeNameException;
import com.sicknasty.objects.Exceptions.ChangeUsernameException;
import com.sicknasty.objects.Exceptions.PasswordErrorException;
import com.sicknasty.objects.Exceptions.UserCreationException;
import com.sicknasty.objects.Exceptions.UserNotFoundException;
import com.sicknasty.persistence.UserPersistence;
import com.sicknasty.persistence.exceptions.DBUsernameExistsException;
import com.sicknasty.persistence.stubs.UserPersistenceStub;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {

    /**
     * creates a new User and test its fields
     * creates a user, tries changing the password, and making sure we can correctly log in with
     * the new password and not the old one
     */
    @Test
    public void testNewUser() throws ChangeNameException, PasswordErrorException, UserCreationException, ChangeUsernameException {
        System.out.println("\nStarting testNewUser");

        User user1 = new User("whatevre","Mr. BOB", "1234567");
        assertNotNull(user1);
        assertEquals("whatevre", user1.getUsername());
        assertEquals("1234567", user1.getPassword());
        assertEquals("Mr. BOB", user1.getName());

        user1.changePassword("12345678");
        assertNotEquals("1234567", user1.getPassword());
        user1.changeUsername("jay1");
        assertNotEquals("whatevre", user1.getUsername());


        System.out.println("Finished testUser");
    }
}