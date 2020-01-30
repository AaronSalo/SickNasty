package com.sicknasty.persistance.stub;

import java.util.ArrayList;
import com.sicknasty.persistance.PostPersistance;

public class PostPersistanceStub implements PostPersistance {
    // an ArrayList containing ALL the posts in the app
    private ArrayList<Post> posts;

    public PostPersistanceStub() {
        this.posts = new ArrayList<Post>();
    }

    public Post GetPostById(int id) {
        for (Post post : this.posts) {
            // return post here
        }

        return null;
    }

    public ArrayList<Post> GetPostsByUser(User user, int limit) {
        ArrayList<Post> userPosts = new ArrayList<Post>();

        for (Post post : this.posts) {
            // if user == post.author then add
        }

        return userPosts;
    }

    public ArrayList<Post> GetPostsByCommunity(Page community, int limit) {
        ArrayList<Post> communityPosts = new ArrayList<Post>();

        for (Post post : this.posts) {
            // if community == post.page then add
        }

        return communityPosts;
    }

    public boolean InsertNewPost() {
        // insert post

        return false;
    }

    public boolean DeletePost(int id) {
        // delete post by id

        return false;
    }

    public boolean DeletePost(Post post) {
        // delete post by object

        return false;
    }
}