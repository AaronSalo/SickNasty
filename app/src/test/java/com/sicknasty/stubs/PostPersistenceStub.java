package com.sicknasty.stubs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import com.sicknasty.objects.Comment;
import com.sicknasty.objects.Page;
import com.sicknasty.objects.Post;
import com.sicknasty.persistence.PostPersistence;
import com.sicknasty.persistence.exceptions.DBPostIDExistsException;
import com.sicknasty.persistence.exceptions.DBPostIDNotFoundException;

import static com.sicknasty.persistence.PostPersistence.FILTER_BY.*;

public class PostPersistenceStub implements PostPersistence {
    // an HashMap containing ALL the posts in the app
    private HashMap<Integer, Post> posts;
    private ArrayList<Comment> comments;

    public PostPersistenceStub() {
        this.posts = new HashMap<Integer, Post>();
    }

    @Override
    public Post getPostById(int id) throws DBPostIDNotFoundException {
        if (!this.posts.containsKey(id)) throw new DBPostIDNotFoundException(id);

        return this.posts.get(id);
    }

    @Override
    public ArrayList<Post> getPostsByPage(Page page, int limit, FILTER_BY filter, boolean accendingOrder) {
        if (page == null) return null;

        ArrayList<Post> pagePosts = new ArrayList<Post>();

        for (Map.Entry<Integer, Post> e : this.posts.entrySet()) {
            Post post = e.getValue();

            if (post.getPageId().getPageName() == page.getPageName()) {
                pagePosts.add(post);
            }
        }

        return pagePosts;
    }

    @Override
    public void insertNewPost(Post post) throws DBPostIDExistsException {
        Post exisitingPost = this.posts.get(post.getPostID());

        if (exisitingPost == null) {
            this.posts.put(post.getPostID(), post);
        } else {
            throw new DBPostIDExistsException(post.getPostID());
        }
    }

    @Override
    public void deletePost(int id) {
        this.posts.remove(id);
    }

    @Override
    public void deletePost(Post post) {
        Post exisitingPost = this.posts.get(post.getPostID());

        if (exisitingPost != null) {
            this.deletePost(exisitingPost.getPostID());
        }

    }

    @Override
    public ArrayList<Comment> getCommentsByPost(Post post, final int limit, final FILTER_BY filter, boolean ascOrder) {
        ArrayList<Comment> result = new ArrayList<Comment>();

        Collections.sort(this.comments, new Comparator<Comment>() {
            @Override
            public int compare(Comment comment1, Comment comment2) {
                int difference = 0;

                if (filter == TIME_CREATED) {

                } else if (filter == AMOUNT_LIKES) {

                } else if (filter == AMOUNT_DISLIKES) {

                }

                return difference;
            }
        });

        if (limit == 0) {
            result = this.comments;
        } else {
            for (int i = 0; i < limit; i++) {
                Comment comment = this.comments.get(i);

                result.add(comment);
            }
        }

        return result;
    }

    @Override
    public void addComment(Comment comment) {
        this.comments.add(comment);
    }
}