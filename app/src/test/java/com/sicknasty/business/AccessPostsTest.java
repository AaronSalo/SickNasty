package com.sicknasty.business;

import com.sicknasty.objects.Comment;
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
import com.sicknasty.persistence.PagePersistence;
import com.sicknasty.persistence.PostPersistence;
import com.sicknasty.persistence.UserPersistence;
import com.sicknasty.persistence.exceptions.DBPageNameExistsException;
import com.sicknasty.persistence.exceptions.DBPostIDExistsException;
import com.sicknasty.persistence.exceptions.DBUsernameExistsException;
import com.sicknasty.stubs.PostPersistenceStub;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AccessPostsTest {

    private AccessPosts accessPostStub;
    private AccessPosts accessPostMock;
    private PostPersistence postPersistenceMock;
    private AccessUsers accessUsers;
    private AccessPages accessPages;

    @Before
    public final void setup(){

        accessUsers = new AccessUsers(mock(UserPersistence.class));
        accessPages = new AccessPages(mock(PagePersistence.class));
        PostPersistence postPersistenceStub = new PostPersistenceStub();
        postPersistenceMock = mock(PostPersistence.class);

        accessPostStub = new AccessPosts(postPersistenceStub);
        accessPostMock = new AccessPosts(postPersistenceMock);
    }

    @Test
    public void testGetPostsByPage() throws NoValidPageException, ChangeNameException, PasswordErrorException, UserCreationException, ChangeUsernameException, DBPostIDExistsException, CaptionTextException
    {
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
    public void testPostInsert() throws NoValidPageException, ChangeNameException, PasswordErrorException, UserCreationException, ChangeUsernameException, DBPostIDExistsException, CaptionTextException
    {
        User newUser = new User("hello", "helloo", "hellooooooo");
        Page page = new PersonalPage(newUser);
        Post post = new Post("this is a test",null,null,1, 1, page);
        accessPostStub.insertPost(post);
    }

    @Test
    public void testPostDelete() throws NoValidPageException, ChangeNameException, PasswordErrorException, UserCreationException, ChangeUsernameException, DBPostIDExistsException, CaptionTextException
    {
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
    public void testCommentFeature() throws ChangeNameException, PasswordErrorException, UserCreationException, ChangeUsernameException, CaptionTextException, NoValidPageException, DBUsernameExistsException, DBPostIDExistsException, DBPageNameExistsException
    {
        User user1 = new User("Jay K","jay1","1234567");

        when(accessUsers.insertUser(user1)).thenReturn(user1);
        accessPages.insertNewPage(user1.getPersonalPage());

        Post post = new Post("HELLO USER",user1,"test",0,0,user1.getPersonalPage());

        postPersistenceMock.insertNewPost(post);
        accessPostMock.insertPost(post);


        Comment comment1 = new Comment(user1,"CONTENT",post.getPostID()), comment;
        postPersistenceMock.addComment(comment1);
        accessPostMock.addComment(comment1);

        ArrayList<Comment> comments = new ArrayList<>();
        comments.add(comment1);


        when(postPersistenceMock.getCommentsByPost(post,1, PostPersistence.FILTER_BY.TIME_CREATED,true)).thenReturn(comments);

        comment = new Comment(user1,"CONTENT1",post.getPostID());

        postPersistenceMock.addComment(comment);
        accessPostMock.addComment(comment);

        comments.add(comment);

        when(postPersistenceMock.getCommentsByPost(post,1, PostPersistence.FILTER_BY.TIME_CREATED,true)).thenReturn(comments);


        verify(postPersistenceMock,times(2)).insertNewPost(post);
        verify(postPersistenceMock,times(2)).addComment(comment);
    }


    @After
    public final void tearDown()
    {
        accessPostMock = null;
        accessPostStub = null;

        postPersistenceMock = null;
    }
}
