package com.sicknasty.business;

import com.sicknasty.objects.Exceptions.ChangeNameException;
import com.sicknasty.objects.Exceptions.ChangeUsernameException;
import com.sicknasty.objects.Exceptions.PasswordErrorException;
import com.sicknasty.objects.Exceptions.UserCreationException;
import com.sicknasty.objects.Page;
import com.sicknasty.objects.PersonalPage;
import com.sicknasty.objects.User;
import com.sicknasty.persistence.PagePersistence;
import com.sicknasty.persistence.exceptions.DBPageNameExistsException;
import com.sicknasty.persistence.exceptions.DBPageNameNotFoundException;
import com.sicknasty.persistence.exceptions.DBUserAlreadyFollowingException;
import com.sicknasty.persistence.stubs.PagePersistenceStub;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class AccessPagesTest {

    private AccessPages accessPagesStub;
    private AccessPages accessPagesMock;


    @Before
    public final void setup()
    {
        PagePersistence pagePersistenceStub,pagePersistenceMock;

        pagePersistenceStub = new PagePersistenceStub();
        pagePersistenceMock = mock(PagePersistence.class);

        accessPagesStub = new AccessPages(pagePersistenceStub);
        accessPagesMock = new AccessPages(pagePersistenceMock);
    }

    @Test
    public void testInsertPage() throws ChangeNameException, PasswordErrorException, UserCreationException, ChangeUsernameException, DBPageNameExistsException, DBPageNameNotFoundException {
            User jay=new User("Jay K","jay","abcmmdef");

            PersonalPage page = new PersonalPage(jay);

            assertTrue("page not added", accessPagesStub.insertNewPage(page));
            assertEquals("username is different", accessPagesStub.getPage("jay").getPageName(), "jay");

            assertNotNull("", accessPagesStub.getPage("jay"));


            assertTrue("object exist but not deleted",accessPagesStub.deletePage("jay"));
            try {
                assertFalse("object not found but deleted", accessPagesStub.deletePage("jay"));
                assertFalse("object not found but deleted", accessPagesStub.deletePage("aaron"));
            } catch (Exception e ){
                System.out.println(e.getMessage());
                fail();
            }
    }

    @Test
    public void testNullPages() throws DBPageNameExistsException, DBPageNameNotFoundException {
        PersonalPage page1 =null;

        assertFalse("page not added", accessPagesStub.insertNewPage(page1));

        User user=null;
        PersonalPage page2 = new PersonalPage(user);

        assertNull("null user's page created and added",accessPagesStub.getPage(page2.getPageName()));

        assertFalse("object exist but not deleted",accessPagesStub.deletePage(page2.getPageName()));
    }

    @Test
    public void testInsertedFields()
    {
        //use mockito
    }

    @Test
    public void deleteNullPage()
    {
        //use mockito

    }

    @Test
    public void testFollowerToPage() throws ChangeNameException, PasswordErrorException, UserCreationException, ChangeUsernameException, DBUserAlreadyFollowingException, DBPageNameNotFoundException, DBPageNameExistsException {
        //use mockito

        User user = new User("jay1","jay2","1234567");
        User user1 = new User("jay1","jay3","1234567");
        Page page = new PersonalPage(user);

        accessPagesStub.insertNewPage(page);

        accessPagesStub.addFollower(page,user1);
        assertEquals(1,accessPagesStub.getPage(user.getUsername()).getFollowers().size());
    }

    @After
    public final void tearDown()
    {
        accessPagesStub  = null;
        accessPagesMock =  null;
    }
}
