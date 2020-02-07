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

public class AccessPostsTest {

    PostPersistence postData;

    public AccessPostsTest (){
        postData =  Service.getPostData();
    }


    @Test
    public void allAccessPostTests() {
        testPostInsert();
        testPostInsert();
    }

    @Test
    public void testPostInsert() {
        User newUser = new User("hello", "helloo", "hellooooooo");
        Page page = new PersonalPage(newUser);
        Post post = new TextPost("this is a test",null,1,1, 1, page);
        assertTrue("the post was not properly inserted", postData.insertNewPost(post));

        ArrayList<Post> test = postData.getPostsByPage(page,2, PostPersistence.FILTER_BY.TIME_CREATED, true);
        assertTrue("the arraylist held by the persistance stub does not contain the post", test.contains(post));
    }

    @Test
    public void testPostDelete() {
        User newUser = new User("hello", "helloo", "hellooooooo");
        Page page = new PersonalPage(newUser);
        Post post = new TextPost("this is a test",null,1,1, 1, page);
        assertTrue("the post was not properly inserted", postData.insertNewPost(post));

        ArrayList<Post> test = postData.getPostsByPage(page,2, PostPersistence.FILTER_BY.TIME_CREATED, true);
        assertTrue("the arraylist held by the persistance stub does not contain the post",test.contains(post));
        //post is inserted

        assertTrue("the deletion failed from the persistence stub", postData.deletePost(post));
        test = postData.getPostsByPage(page,2, PostPersistence.FILTER_BY.TIME_CREATED, true);
        assertFalse("the array held by the persistence stub still holds the post" , test.contains(post)); //the post should no longer be in the arrayList
    }
}
