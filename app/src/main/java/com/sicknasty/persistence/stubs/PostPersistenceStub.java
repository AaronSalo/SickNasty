package com.sicknasty.persistence.stubs;

import java.util.ArrayList;
import java.util.HashMap;

import com.sicknasty.objects.Page;
import com.sicknasty.objects.Post;
import com.sicknasty.persistence.PostPersistence;

public class PostPersistenceStub implements PostPersistence {
    // an HashMap containing ALL the posts in the app
    private HashMap<Integer, Post> posts;

    public PostPersistenceStub() {
        this.posts = new HashMap<Integer, Post>();
    }

    @Override
    public Post GetPostById(int id) {
        return this.posts.get(id);
    }

    @Override
    public ArrayList<Post> GetPostsByPage(Page page, int limit, FILTER_BY filter, boolean accendingOrder) {
        // this function is up for discussion
        return null;
    }

    @Override
    public boolean InsertNewPost(Post post) {
        Post exisitingPost = this.posts.get(post.getPostID());

        if (exisitingPost == null) {
            this.posts.put(post.getPostID(), post);

            return true;
        }

        return false;
    }

    @Override
    public boolean DeletePost(int id) {
        // remove the post, if it removes then result will be the Post object
        // if it did not fine an id, it will return null
        Post result = this.posts.remove(id);

        return result != null;
    }

    @Override
    public boolean DeletePost(Post post) {
        Post exisitingPost = this.posts.get(post.getPostID());

        if (exisitingPost == null) {
            return false;
        }

        // i dont like this.
        // the existance of this function can be discussed
        return this.DeletePost(exisitingPost.getPostID());
    }
}