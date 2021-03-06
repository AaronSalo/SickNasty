package com.sicknasty.business;

import com.sicknasty.application.Service;
import com.sicknasty.objects.Comment;
import com.sicknasty.objects.Exceptions.CaptionTextException;
import com.sicknasty.objects.Exceptions.ChangeNameException;
import com.sicknasty.objects.Exceptions.ChangeUsernameException;
import com.sicknasty.objects.Exceptions.NoValidPageException;
import com.sicknasty.objects.Exceptions.PasswordErrorException;
import com.sicknasty.objects.Exceptions.UserCreationException;
import com.sicknasty.objects.Exceptions.UserNotFoundException;
import com.sicknasty.objects.PersonalPage;
import com.sicknasty.objects.Post;
import com.sicknasty.objects.User;
import com.sicknasty.persistence.exceptions.DBPageNameExistsException;
import com.sicknasty.persistence.exceptions.DBPostIDExistsException;
import com.sicknasty.persistence.exceptions.DBUsernameExistsException;
import com.sicknasty.persistence.exceptions.DBUsernameNotFoundException;


import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;


public class AccessPostsIT {

    private AccessPosts posts;
    private AccessUsers users;
    private AccessPages pages;


    @Before
    public void setUp()
    {

        Service.initTestDatabase();
        //set up the db
        posts = new AccessPosts();
        users = new AccessUsers();
        pages = new AccessPages();
    }

    @Test
    public void testInsertPost() throws UserNotFoundException, NoValidPageException, DBUsernameNotFoundException, ChangeNameException, PasswordErrorException, UserCreationException, ChangeUsernameException, DBPostIDExistsException, DBUsernameExistsException, DBPageNameExistsException, CaptionTextException {

        System.out.println("Starting insertPostTest::");
        User user1 = new User("Jay K","jay1","1234567");
        users.insertUser(user1);
        pages.insertNewPage(user1.getPersonalPage());

        Post newPost = new Post("Caption is nice", user1, "some random path doesn't matter", 0, 0, user1.getPersonalPage());

        posts.insertPost(newPost);
        assertEquals(newPost.getNumberOfDislikes(),0);
        assertEquals(newPost.getNumberOfLikes(),0);
        assertEquals(newPost.getPageId(),user1.getPersonalPage());
        assertEquals(newPost.getPath(),"some random path doesn't matter");
        assertEquals(newPost.getText(),"Caption is nice");
        assertEquals(newPost.getUserId(),user1);

        posts.deletePost(newPost);
        users.deleteUser("jay1");
        System.out.println("Finished insertPostTest");
    }

    @Test(expected = DBPostIDExistsException.class)
    public void testDuplicatePost() throws NoValidPageException, ChangeNameException, PasswordErrorException, UserCreationException, ChangeUsernameException, DBPostIDExistsException, DBUsernameExistsException, DBPageNameExistsException, CaptionTextException {
        System.out.println("Starting duplicatePostTest::");
        User user1 = new User("Jay K","jay1","1234567");
        Post newPost = new Post("Caption is nice",user1,"some random path doesn't matter",0,0,user1.getPersonalPage());

        users.insertUser(user1);
        pages.insertNewPage(user1.getPersonalPage());

        posts.insertPost(newPost);
        posts.insertPost(newPost);     //it is adding same post twice

        posts.deletePost(newPost.getPostID());
        System.out.println("Finished duplicatePostTest");

    }

    @Test
    public void testGetPost() throws NoValidPageException, ChangeNameException, PasswordErrorException, UserCreationException, ChangeUsernameException, DBPostIDExistsException, DBPageNameExistsException, DBUsernameExistsException, CaptionTextException {
        User user1 = new User("Jay K","jay1","1234567");
        PersonalPage page = new PersonalPage(user1);

        users.insertUser(user1);
        pages.insertNewPage(user1.getPersonalPage());
        Post newPost = new Post("Caption is nice",user1,"some random path doesn't matter",0,0,user1.getPersonalPage());

        posts.insertPost(newPost);
        assertEquals(posts.getPostsByPage(page).size(),1);
        assertEquals(posts.getPostsByPage(page).get(0).getPostID(),newPost.getPostID());

        posts.deletePost(newPost);

        System.out.println("Finished testGetPost");
    }


    @Test
    public void testRemovePost() throws NoValidPageException, ChangeNameException, PasswordErrorException, UserCreationException, ChangeUsernameException, DBPostIDExistsException, DBUsernameExistsException, DBPageNameExistsException, CaptionTextException {
        System.out.println("Started testRemovePost");

        User user1 = new User("Jay K","jay1","1234567");
        PersonalPage page = new PersonalPage(user1);

        users.insertUser(user1);
        pages.insertNewPage(user1.getPersonalPage());

        Post newPost = new Post("Caption is nice",user1,"some random path doesn't matter",0,0,user1.getPersonalPage());

        posts.insertPost(newPost);
        posts.deletePost(newPost);

        assertFalse(posts.getPostsByPage(page).remove(newPost));

        System.out.println("Finished testRemovePost");
    }

    @Test
    public void testNotExistPost() throws NoValidPageException, ChangeNameException, PasswordErrorException, UserCreationException, ChangeUsernameException, DBPostIDExistsException, CaptionTextException {
        System.out.println("Started testNotExist");

        User user1 = new User("Jay K","jay1","1234567");
        PersonalPage page = new PersonalPage(user1);

        Post newPost = new Post("Caption is nice",user1,"some random path doesn't matter",0,0,page);

        assertFalse(posts.getPostsByPage(page).contains(newPost));
        posts.deletePost(newPost);

        System.out.println("Finished testNotExistPost");

    }

    @Test
    public void testDeleteById() throws NoValidPageException, ChangeNameException, PasswordErrorException, UserCreationException, ChangeUsernameException, DBPostIDExistsException, DBUsernameExistsException, DBPageNameExistsException, CaptionTextException {
        System.out.println("Started testDeleteById");
        User user1 = new User("Jay K","jay1","1234567");
        PersonalPage page = new PersonalPage(user1);

        users.insertUser(user1);
        pages.insertNewPage(user1.getPersonalPage());

        Post newPost=new Post("Caption is nice",user1,"some random path doesn't matter",0,0,page);

        posts.insertPost(newPost);
        posts.deletePost(newPost.getPostID());

        assertFalse(posts.getPostsByPage(page).contains(newPost));

        System.out.println("Finished testDeleteById");

    }

    @Test
    public void testCommentFeature() throws CaptionTextException, NoValidPageException, ChangeNameException, PasswordErrorException, UserCreationException, ChangeUsernameException, DBPageNameExistsException, DBUsernameExistsException, DBPostIDExistsException
    {
        User user1 = new User("Jay K","jay1","1234567");
        users.insertUser(user1);
        pages.insertNewPage(user1.getPersonalPage());

        Post post = new Post("HELLO USER",user1,"test",0,0,user1.getPersonalPage());
        posts.insertPost(post);
        Comment comment = new Comment(user1,"NICE PIC", post.getPostID());

        posts.addComment(comment);

        ArrayList<Comment> postComments = posts.getComments(post);
        assertEquals(1, postComments.size());
        assertEquals(comment, postComments.get(0));

        posts.addComment(comment);

        postComments = posts.getComments(post);
        assertEquals(2, postComments.size());
        assertEquals(comment, postComments.get(1));



    }
}
