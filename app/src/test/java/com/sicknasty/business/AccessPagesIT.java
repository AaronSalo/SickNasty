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
import com.sicknasty.persistence.exceptions.DBUsernameExistsException;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AccessPagesIT {

    AccessPages pages;
    AccessUsers users;
    User jay;
    PersonalPage page;
    @Before
    public void setUp() throws DBUsernameExistsException,ChangeNameException, PasswordErrorException, UserCreationException, ChangeUsernameException {
        Service.initTestDatabase();
        pages = new AccessPages();
        users=  new AccessUsers();
        jay=new User("Jay K","jay","abcmmdef");
        users.insertUser(jay);
        page=new PersonalPage(jay);

    }

    @Test(expected = DBPageNameExistsException.class)
    public void testInsertPage() throws DBPageNameExistsException, DBPageNameNotFoundException {

        pages.insertNewPage(page);
        assertEquals("username is different",pages.getPage("jay").getPageName(),"jay");

        assertNotNull("",pages.getPage("jay"));
        page=new PersonalPage(jay);

        pages.insertNewPage(page);
        pages.deletePage("jay");

        pages.deletePage("jay");

        pages.deletePage("aaron");

    }
    @Test(expected = DBPageNameNotFoundException.class)
    public void testNullPages() throws DBPageNameExistsException, DBPageNameNotFoundException {
        assertNotNull(pages);
        assertNull("page does not exist",pages.getPage("jay"));

        User user1=null;
        PersonalPage page2 = new PersonalPage(user1);

        pages.insertNewPage(page2);

        assertNull("null user's page created and added",pages.getPage(page2.getPageName()));

        pages.deletePage(page2.getPageName());
    }
    @Test(expected = DBPageNameNotFoundException.class)
    public void testGetPage() throws DBPageNameExistsException, DBPageNameNotFoundException {

        assertNull("page has been somehow added",pages.getPage("jay").getPageName());
        pages.insertNewPage(page);
        assertNotNull("page is null",pages.getPage("jay").getPageName());
        assertEquals("user's page created but not to corresponding user",pages.getPage("jay").getPageName(),"jay");

        pages.deletePage("jay");
    }

    @Test(expected = DBPageNameNotFoundException.class)
    public void testDeletePage() throws DBPageNameExistsException, DBPageNameNotFoundException {
        pages.insertNewPage(page);
        pages.deletePage("jay");
        pages.deletePage("jay");
        pages.getPage("jay");
    }

    @Test
    public void testDeleteNullPage() throws DBPageNameExistsException {
        pages.insertNewPage(page);
        pages.deletePage("jayqs");
        pages.deletePage("jay");
    }

}
