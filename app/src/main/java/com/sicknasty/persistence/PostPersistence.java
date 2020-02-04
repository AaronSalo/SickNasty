package com.sicknasty.persistence;

import java.util.ArrayList;

public interface PostPersistence {
    public enum FILTER_BY {TIME_CREATED, AMOUNT_LIKES};
    /**
     * Returns a Post specified by it's unique ID.
     * 
     * @param   id  the unique ID of the Post
     * @return      the Post corresponding to the ID, otherwise it will return null
     */
    public Post GetPostById(int id);

    /**
     * Returns a specified number of Posts shared to a Page (this includes both personal and community Pages).
     * 
     * @param   page the Page to get all the posts from
     * @param   limit   the maximum number of posts to fetch
     * @param   filter  the filter to sort Page posts by
     * @param   accendingOrder set true to get an ArrayList sorted in accending order
     * @return          ArrayList of type Post
     */
    public ArrayList<Post> GetPostsByPage(Page page, int limit, FILTER_BY filter, boolean accendingOrder);

    /**
     * Inserts a new Post.
     * 
     * @return      returns true on successful insert, otherwise return false
     */
    public boolean InsertNewPost();

    /**
     * Deletes a Post specified by it's unique ID.
     * 
     * @param   id  the unique ID of the Post
     * @return      returns true on success, otherwise return false
     */
    public boolean DeletePost(int id);

    /**
     * Deletes a Post specified by a Post object.
     * 
     * @param   post a Post object to delete
     * @return      returns true on success, otherwise return false
     */
    public boolean DeletePost(Post post);
}