package com.sicknasty.business;

import com.sicknasty.objects.Exceptions.CaptionTextException;
import com.sicknasty.objects.Exceptions.ChangeNameException;
import com.sicknasty.objects.Exceptions.ChangeUsernameException;
import com.sicknasty.objects.Exceptions.NoValidPageException;
import com.sicknasty.objects.Exceptions.PasswordErrorException;
import com.sicknasty.objects.Exceptions.UserCreationException;
import com.sicknasty.objects.Page;
import com.sicknasty.objects.PersonalPage;
import com.sicknasty.objects.Post;
import com.sicknasty.objects.User;
import com.sicknasty.persistence.PostPersistence;
import com.sicknasty.persistence.exceptions.DBPostIDExistsException;
import com.sicknasty.stubs.PostPersistenceStub;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;

public class AccessPostsTest {


    private AccessPosts accessPostStub;
    private AccessPosts accessPostMock;

    @Before
    public final void setup(){

        PostPersistence postPersistenceMock,postPersistenceStub;

        postPersistenceStub = new PostPersistenceStub();
        postPersistenceMock = mock(PostPersistence.class);

        accessPostStub = new AccessPosts(postPersistenceStub);
        accessPostMock = new AccessPosts(postPersistenceMock);
    }

    @Test
    public void testGetPostsByPage() throws NoValidPageException, ChangeNameException, PasswordErrorException, UserCreationException, ChangeUsernameException, DBPostIDExistsException, CaptionTextException {
        User newUser = new User("hello", "helloo", "hellooooooo");
        Page page = new PersonalPage(newUser);
        Post post = new Post("this is a test",null,null,1, 1, page);
        accessPostStub.insertPost(post);

        Page newPage=new PersonalPage(new User("test", "test", "testing1234"));
        ArrayList<Post> test = accessPostStub.getPostsByPage(newPage);
        assertFalse("the post should not be in this list, it was posted to a different page", test.contains(post));

        assertTrue("the post is not in the pages list of posts", accessPostStub.getPostsByPage(page).contains(post));
    }

    @Test
    public void testPostInsert() throws NoValidPageException, ChangeNameException, PasswordErrorException, UserCreationException, ChangeUsernameException, DBPostIDExistsException, CaptionTextException {
        User newUser = new User("hello", "helloo", "hellooooooo");
        Page page = new PersonalPage(newUser);
        Post post = new Post("this is a test",null,null,1, 1, page);
        accessPostStub.insertPost(post);
    }

    @Test
    public void testPostDelete() throws NoValidPageException, ChangeNameException, PasswordErrorException, UserCreationException, ChangeUsernameException, DBPostIDExistsException, CaptionTextException {
        User newUser = new User("hello", "helloo", "hellooooooo");
        Page page = new PersonalPage(newUser);
        Post post = new Post("this is a test",null,null,1, 1, page);

        accessPostStub.insertPost(post);

        accessPostStub.deletePost(post);

        accessPostStub.deletePost(post);

        Post aDifferentPost = new Post("this is a test",newUser,null,1, 1, page);

        accessPostStub.deletePost(aDifferentPost);
    }
    @Test
    public void deleteNullPost()
    {
        //normal unit test
    }
    @Test
    public void testGetPostsByFilter()
    {
        //use mockito
    }

    @Test
    public void testInsertedFields()
    {

    }

    @Test
    public void testDeleteById()
    {


    }

    @Test
    public void testGetDeleted()
    {

    }


    @After
    public final void tearDown()
    {
        accessPostMock = null;
        accessPostStub = null;
    }
}
