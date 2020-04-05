package com.sicknasty.business;

import com.sicknasty.application.Service;
import com.sicknasty.objects.CommunityPage;
import com.sicknasty.objects.Exceptions.ChangeNameException;
import com.sicknasty.objects.Exceptions.ChangeUsernameException;
import com.sicknasty.objects.Exceptions.InvalidCommunityPageNameException;
import com.sicknasty.objects.Exceptions.PasswordErrorException;
import com.sicknasty.objects.Exceptions.UserCreationException;
import com.sicknasty.objects.Page;
import com.sicknasty.objects.PersonalPage;
import com.sicknasty.objects.User;
import com.sicknasty.persistence.exceptions.DBPageNameExistsException;
import com.sicknasty.persistence.exceptions.DBPageNameNotFoundException;
import com.sicknasty.persistence.exceptions.DBUserAlreadyFollowingException;
import com.sicknasty.persistence.exceptions.DBUsernameExistsException;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class AccessPagesIT {

    private AccessPages pages;
    private User jay;
    private PersonalPage page;
    private AccessUsers users;

    @Before
    public void setUp() throws DBUsernameExistsException,ChangeNameException, PasswordErrorException, UserCreationException, ChangeUsernameException
    {
        Service.initTestDatabase();
        pages = new AccessPages();
        users = new AccessUsers();
        jay = new User("Jay K","jay","abcmmdef");
        users.insertUser(jay);
        page = new PersonalPage(jay);
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
    @Test
    public void testAllCommunityPages() throws InvalidCommunityPageNameException, DBPageNameExistsException, DBPageNameNotFoundException, ChangeNameException, PasswordErrorException, UserCreationException, ChangeUsernameException, DBUsernameExistsException
    {

        ArrayList<String> communityPages = pages.getAllCommunityPages();
        User user = new User("Jay K","jay2","1234567");
        //even though personal page was added
        assertEquals(communityPages.size(),0);

        Page communityPage1 = new CommunityPage(jay,"COMPUTER");
        Page communityPage2 = new CommunityPage(jay,"ARTS");
        Page personalPage1 = new PersonalPage(user);

        pages.insertNewPage(communityPage1);
        pages.insertNewPage(communityPage2);

        //make sure it is inserted
        assertNotNull(pages.getPage("COMPUTER"));
        assertNotNull(pages.getPage("ARTS"));

        communityPages = pages.getAllCommunityPages();
        assertEquals(communityPages.size(),2);

        users.insertUser(user);
        pages.insertNewPage(personalPage1);

        communityPages = pages.getAllCommunityPages();
        //should still be 2
        assertEquals(communityPages.size(),2);

        assertEquals("COMPUTER",communityPages.get(1));
        assertEquals("ARTS",communityPages.get(0));
    }

    @Test
    public void testFollowPages() throws ChangeNameException, PasswordErrorException, UserCreationException, ChangeUsernameException, DBUsernameExistsException, DBPageNameExistsException, DBUserAlreadyFollowingException, DBPageNameNotFoundException, InvalidCommunityPageNameException {
        User user = new User("Jay K","jay2","1234567");
        Page personalPage1 = new CommunityPage(user,"COMPUTER");

        users.insertUser(user);
        pages.insertNewPage(page);
        pages.insertNewPage(user.getPersonalPage());
        pages.insertNewPage(personalPage1);

        pages.addFollower(personalPage1,jay);
        //pages.addFollower(personalPage1,user);
        pages.addFollower(page,user);

        assertEquals(pages.getPage("COMPUTER").getFollowers().size(),1);
        assertEquals(pages.getPage("COMPUTER").getFollowers().get(1), jay);

        assertEquals(pages.getPage("jay").getFollowers().size(),1);
        assertEquals(pages.getPage("jay").getFollowers().get(0),user);

    }

}
