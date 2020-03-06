package com.sicknasty.business;

import com.sicknasty.application.Service;
import com.sicknasty.objects.Exceptions.ChangeNameException;
import com.sicknasty.objects.Exceptions.ChangeUsernameException;
import com.sicknasty.objects.Exceptions.PasswordErrorException;
import com.sicknasty.objects.Exceptions.UserCreationException;
import com.sicknasty.objects.PersonalPage;
import com.sicknasty.objects.User;
import com.sicknasty.persistence.exceptions.DBPageNameExistsException;
import com.sicknasty.persistence.exceptions.DBPageNameNotFoundException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class AccessPagesIT {

    AccessPages pages = new AccessPages();

    @Before
    public void setUp()
    {
        Service.initTestDatabase();
        pages = new AccessPages();
    }

    @Test(expected = DBPageNameExistsException.class)
    public void testInsertPage() throws ChangeNameException, PasswordErrorException, UserCreationException, ChangeUsernameException, DBPageNameExistsException, DBPageNameNotFoundException {
        User jay=new User("Jay K","jay","abcmmdef");

        PersonalPage page = new PersonalPage(jay);

        assertTrue("page not added",pages.insertNewPage(page));
        assertEquals("username is different",pages.getPage("jay").getPageName(),"jay");

        assertNotNull("",pages.getPage("jay"));

        page=new PersonalPage(jay);
        assertFalse("two pages with same username added",pages.insertNewPage(page));
        assertTrue("object exist but not deleted",pages.deletePage("jay"));

        assertFalse("object not found but deleted",pages.deletePage("jay"));

        assertFalse("object not found but deleted",pages.deletePage("aaron"));

    }
    @Test(expected = DBPageNameNotFoundException.class)
    public void testNullPages() throws DBPageNameExistsException, DBPageNameNotFoundException {
        PersonalPage page =null;

        assertFalse("page not added",pages.insertNewPage(page));
        assertNull("page does not exist",pages.getPage("jay"));

        User user=null;
        PersonalPage page1 = new PersonalPage(user);

        assertFalse("null user's page created and added",pages.insertNewPage(page1));

        assertNull("null user's page created and added",pages.getPage(page1.getPageName()));

        assertFalse("object exist but not deleted",pages.deletePage(page1.getPageName()));
    }
    @Test(expected = DBPageNameNotFoundException.class)
    public void testGetPage() throws ChangeUsernameException, ChangeNameException, PasswordErrorException, UserCreationException, DBPageNameExistsException, DBPageNameNotFoundException {
        User jay=new User("Jay K","jay","abcmmdef");
        assertNull("page has been somehow added",pages.getPage("jay").getPageName());
        assertFalse("page not added",pages.insertNewPage(new PersonalPage(jay)));
        assertNotNull("page is null",pages.getPage("jay").getPageName());
        assertEquals("user's page created but not to corresponding user",pages.getPage("jay").getPageName(),"jay");

        assertTrue("page is null",pages.deletePage("jay"));
    }

    @Test(expected = DBPageNameNotFoundException.class)
    public void testDeletePage() throws ChangeUsernameException, ChangeNameException, PasswordErrorException, UserCreationException, DBPageNameExistsException, DBPageNameNotFoundException {
        User jay=new User("Jay K","jay","abcmmdef");
        assertTrue("page not added",pages.insertNewPage(new PersonalPage(jay)));
        assertTrue("null user's page created and added",pages.deletePage("jay"));
        assertFalse("already deleted page",pages.deletePage("jay"));
        assertNull("null user's page created and added",pages.getPage("jay"));
    }

    @Test
    public void testDeleteNullPage() throws ChangeUsernameException, ChangeNameException, PasswordErrorException, UserCreationException, DBPageNameExistsException, DBPageNameNotFoundException {
        User jay=new User("Jay K","jay","abcmmdef");
        assertTrue("page not added",pages.insertNewPage(new PersonalPage(jay)));
        assertFalse("page deleted which was not created",pages.deletePage("jayqs"));
        assertTrue("page was somehow not deleted",pages.deletePage("jay"));
        assertFalse("already deleted page",pages.deletePage("jay"));
    }

    @After
    public void tearDown()
    {
        // reset DB
//        this.tempDB.delete();
    }
}
