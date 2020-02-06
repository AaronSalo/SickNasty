package com.sicknasty.objects;

import org.junit.Test;

import static org.junit.Assert.*;

public class PostTest {

    @Test
    public void displayPost() {
    }

    @Test
    public void checkPostNum() {        //checks to see that the global post id of posts, compared to the local post id.
                                        // using different post objects

        User user = new User("Will", "I.AM");
        Page page = new PersonalPage("pageTest", user);

        Post post1 = new PicturePost("testText", user,"N/A", "N/A", 123, 1,2,page);
        Post post2 = new PicturePost("testText", user,"N/A", "N/A", 123, 2,4,page);
        Post post3 = new PicturePost("testText", user,"N/A", "N/A", 123, 3,6,page);
        Post post4 = new PicturePost("testText", user,"N/A", "N/A", 123, 4,8,page);

        assertEquals("Posts local and global id do not match when post is constructed",3,post3.getPostID());

        assertEquals("global post id is not the same for local last post id and global first post id",post4.getPostID(),post1.getGlobalpostID());


    }

}