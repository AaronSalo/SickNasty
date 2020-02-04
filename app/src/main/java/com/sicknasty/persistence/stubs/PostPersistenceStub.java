package com.sicknasty.persistence.stubs;

import java.util.HashMap;

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
    public boolean InsertNewPost() {
        // waiting on Post implementation
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
        int id = post.GET_ID_HERE;

        // i dont like this.
        // the existance of this function can be discussed
        return this.DeletePost(id);
    }
}