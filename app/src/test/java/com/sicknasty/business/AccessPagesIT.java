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

    @Test
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
    @Test
    public void testNullPages() throws DBPageNameExistsException, DBPageNameNotFoundException {
        PersonalPage page =null;

        assertFalse("page not added",pages.insertNewPage(page));
        assertEquals("page does not exist",pages.getPage("jay"),null);

        User user=null;
        PersonalPage page1 = new PersonalPage(user);

        assertFalse("null user's page created and added",pages.insertNewPage(page1));

        assertEquals("null user's page created and added",pages.getPage(page1.getPageName()),null);

        assertFalse("object exist but not deleted",pages.deletePage(page1.getPageName()));
    }
    @After
    public void tearDown()
    {
        // reset DB
//        this.tempDB.delete();
    }
}
