package com.sicknasty.business;

import com.sicknasty.application.Service;
import com.sicknasty.objects.Post;
import com.sicknasty.objects.Page;
import com.sicknasty.persistence.PostPersistence;
import com.sicknasty.persistence.stubs.PostPersistenceStub;

import java.util.ArrayList;

public class GetPosts {

    private int postGetLimit = 15; //the max amount of posts to get

    private PostPersistenceStub postHandler;
    private ArrayList<Post> posts;

    public GetPosts() {
        postHandler = Service.getPostData();
    }

    /**
     * returns the posts on on a given page
    * @param    page    the page we want to fetch the pages from
    * @return   returns an ArrayList holding a number of posts equal to postGetLimit
     */
    public ArrayList<Post> getPostsByPage(Page page) {
        return postHandler.GetPostsByPage(page, postGetLimit,
                PostPersistence.FILTER_BY.TIME_CREATED, true);
    }

    /**
     *@param    post   the post we want to insert into the db
     *@return true if the post was successful, false if the post was unsuccessful
     */
    public boolean insertPost(Post post) {
        return postHandler.InsertNewPost(post);
    }

    /**
     *@param    id   the id of the post we want to delete from the db
     *@return true if the deletion was successful, false if the deletion was unsuccessful
     */
    public boolean deletePost(int id) {
        return postHandler.DeletePost(id);
    }

}