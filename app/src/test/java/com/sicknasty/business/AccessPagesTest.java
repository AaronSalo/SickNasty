package com.sicknasty.business;

import com.sicknasty.objects.CommunityPage;
import com.sicknasty.objects.Exceptions.ChangeNameException;
import com.sicknasty.objects.Exceptions.ChangeUsernameException;
import com.sicknasty.objects.Exceptions.InvalidCommunityPageNameException;
import com.sicknasty.objects.Exceptions.PasswordErrorException;
import com.sicknasty.objects.Exceptions.UserCreationException;
import com.sicknasty.objects.Page;
import com.sicknasty.objects.PersonalPage;
import com.sicknasty.objects.User;
import com.sicknasty.persistence.PagePersistence;
import com.sicknasty.persistence.UserPersistence;
import com.sicknasty.persistence.exceptions.DBPageNameExistsException;
import com.sicknasty.persistence.exceptions.DBPageNameNotFoundException;
import com.sicknasty.persistence.exceptions.DBUserAlreadyFollowingException;
import com.sicknasty.persistence.exceptions.DBUsernameExistsException;
import com.sicknasty.stubs.PagePersistenceStub;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertNotNull;

public class AccessPagesTest {

    private AccessPages accessPagesStub;
    private AccessUsers accessUsers;
    private AccessPages accessPagesMock;
    private PagePersistence pagePersistenceStub,pagePersistenceMock;

    @Before
    public final void setup()
    {
        accessUsers = new AccessUsers(mock(UserPersistence.class));
        pagePersistenceStub = new PagePersistenceStub();
        pagePersistenceMock = mock(PagePersistence.class);

        accessPagesStub = new AccessPages(pagePersistenceStub);
        accessPagesMock = new AccessPages(pagePersistenceMock);
    }

    @Test
    public void testInsertPage() throws ChangeNameException, PasswordErrorException, UserCreationException, ChangeUsernameException, DBPageNameExistsException, DBPageNameNotFoundException {
            User jay=new User("Jay K","jay","abcmmdef");

            PersonalPage page = new PersonalPage(jay);

            accessPagesStub.insertNewPage(page);
            accessPagesStub.getPage("jay").getPageName();

            assertNotNull("", accessPagesStub.getPage("jay"));


            accessPagesStub.deletePage("jay");
            try {
                accessPagesStub.deletePage("jay");
                accessPagesStub.deletePage("aaron");
            } catch (Exception e ){
                System.out.println(e.getMessage());
                fail();
            }
    }

    @Test(expected = NullPointerException.class)
    public void testNullPages() throws DBPageNameExistsException, DBPageNameNotFoundException {
        PersonalPage page1 =null;

        accessPagesStub.insertNewPage(page1);

        User user=null;
        PersonalPage page2 = new PersonalPage(user);

        assertNull("null user's page created and added",accessPagesStub.getPage(page2.getPageName()));

        accessPagesStub.deletePage(page2.getPageName());
    }

    @Test
    public void testFollowerToPage() throws ChangeNameException, PasswordErrorException, UserCreationException, ChangeUsernameException, DBUserAlreadyFollowingException, DBPageNameNotFoundException, DBPageNameExistsException, DBUsernameExistsException {
        //use mockito

        User user = new User("jay1","jay2","1234567");
        User user1 = new User("jay1","jay3","1234567");
        Page page = new PersonalPage(user);

        accessUsers.insertUser(user);

        pagePersistenceMock.insertNewPage(page);
        accessPagesMock.insertNewPage(page);

        when(pagePersistenceMock.getPage("jay2")).thenReturn(page);

        accessPagesMock.addFollower(page,user1);
//        assertEquals(page.getFollowers().size(),2);

        verify(pagePersistenceMock,times(2)).insertNewPage(page);
        verify(pagePersistenceMock).addFollower(page,user1);
    }

    @Test
    public void testAllCommunityPages() throws ChangeNameException, PasswordErrorException, UserCreationException, ChangeUsernameException, InvalidCommunityPageNameException, DBPageNameExistsException
    {
        User user = new User("jay1","jay2","1234567");
        Page communityPage = new CommunityPage(user,"COMPUTER");
        Page communityPage1 = new CommunityPage(user,"ARTS");

        pagePersistenceMock.insertNewPage(communityPage);
        pagePersistenceMock.insertNewPage(communityPage1);

        ArrayList<String> communityList = new ArrayList<>();
        communityList.add(communityPage.getPageName());
        communityList.add(communityPage1.getPageName());

        doReturn(communityList).when(pagePersistenceMock).getAllCommunityPageNames();
        assertEquals(communityList,accessPagesMock.getAllCommunityPages());
        verify(pagePersistenceMock).insertNewPage(communityPage);
        verify(pagePersistenceMock).insertNewPage(communityPage1);

    }

    //do mockito for community pages
    @After
    public final void tearDown()
    {
        pagePersistenceMock = null;
        pagePersistenceStub = null;
        accessPagesStub  = null;
        accessPagesMock =  null;
    }
}
