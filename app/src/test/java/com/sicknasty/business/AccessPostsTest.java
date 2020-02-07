package com.sicknasty.business;

import com.sicknasty.objects.Page;
import com.sicknasty.objects.PersonalPage;
import com.sicknasty.objects.Post;
import com.sicknasty.objects.TextPost;
import com.sicknasty.objects.User;
import com.sicknasty.persistence.PostPersistence;
import com.sicknasty.persistence.stubs.PostPersistenceStub;
import com.sicknasty.application.Service;

import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class AccessPostsTest {

    AccessPosts access;

    public AccessPostsTest (){
        access =  new AccessPosts();
    }


    @Test
    public void allAccessPostTests() {
        testPostInsert();
        testPostInsert();
        testGetPostsByPage();
    }


    @Test
    public void testGetPostsByPage() {
        User newUser = new User("hello", "helloo", "hellooooooo");
        Page page = new PersonalPage(newUser);
        Post post = new TextPost("this is a test",null,1,1, 1, page);
        //insert the post
        assertTrue("the post was not properly inserted", access.insertPost(post));

        //try searching for the post in a different page that what it was placed in
        ArrayList<Post> test = access.getPostsByPage(new PersonalPage(new User("test", "test", "testing1234")));
        assertFalse("the post should not be in this list, it was posted to a different page", test.contains(post));

        //Check if the post is in the page we inserted into's list of posts
        assertTrue("the post is not in the pages list of posts", access.getPostsByPage(page).contains(post));
    }

    @Test
    public void testPostInsert() {
        User newUser = new User("hello", "helloo", "hellooooooo");
        Page page = new PersonalPage(newUser);
        Post post = new TextPost("this is a test",null,1,1, 1, page);
        assertTrue("the post was not properly inserted", access.insertPost(post));
    }

    @Test
    public void testPostDelete() {
        User newUser = new User("hello", "helloo", "hellooooooo");
        Page page = new PersonalPage(newUser);
        Post post = new TextPost("this is a test",null,1,1, 1, page);
        assertTrue("the post was not properly inserted", access.insertPost(post));


        //try deleting the post
        assertTrue("the deletion failed from the persistence stub", access.deletePost(post));
        //try deleting the same post again (should fail)
        assertFalse("We somehow deleted the same post twice", access.deletePost(post));

        Post aDifferentPost = new TextPost("this is a test",newUser,1,1, 1, page);
        //try to delete a post that has not been added
        assertFalse("deleted a post that was not inserted to the db", access.deletePost(aDifferentPost));
    }
}
