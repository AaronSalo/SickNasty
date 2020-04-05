package com.sicknasty.business;

import com.sicknasty.application.Service;
import com.sicknasty.objects.Comment;
import com.sicknasty.objects.Exceptions.NoValidPageException;
import com.sicknasty.objects.Post;
import com.sicknasty.objects.Page;
import com.sicknasty.persistence.PostPersistence;
import com.sicknasty.persistence.exceptions.DBPostIDExistsException;

import java.util.ArrayList;

public class AccessPosts {

    private int postGetLimit = 15; //the max amount of posts to get

    private PostPersistence postHandler;

    /**
     * this constructor is to get the hsql db
     *
     */
    public AccessPosts() {
        postHandler = Service.getPostData();
    }

    /** this is for Unit Test
     *test are performed through business layer
     *-Jay
     */
    public AccessPosts(final PostPersistence postPersistence){ postHandler = postPersistence; }

    /**
     * returns the posts on on a given page
    * @param    page    the page we want to fetch the pages from
    * @return   returns an ArrayList holding a number of posts equal to postGetLimit
     */
    public ArrayList<Post> getPostsByPage(Page page) throws NoValidPageException {
        return postHandler.getPostsByPage(page, postGetLimit,
                PostPersistence.FILTER_BY.TIME_CREATED, true);
    }

    /**
     *@param    post   the post we want to insert into the db
     *@return true if the post was successful, false if the post was unsuccessful
     */
    public boolean insertPost(Post post) throws DBPostIDExistsException {
        return postHandler.insertNewPost(post);
    }

    /**
     *@param    id   the id of the post we want to delete from the db
     *@return true if the deletion was successful, false if the deletion was unsuccessful
     */
    public boolean deletePost(int id) {
        return postHandler.deletePost(id);
    }

    /**
     *@param    post    the post we want to delete from the db
     *@return true if the deletion was successful, false if the deletion was unsuccessful
     */
    public boolean deletePost(Post post) {
        return postHandler.deletePost(post);
    }

    public void addComment(Comment comment) { postHandler.addComment(comment);}

    public ArrayList<Comment> getCommentsByPost (Post post, int limit) {
        //i think always just filter by time created for now
        return postHandler.getCommentsByPost(post, limit, PostPersistence.FILTER_BY.TIME_CREATED, true); }
}
