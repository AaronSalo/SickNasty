package com.sicknasty.persistence;

import com.sicknasty.objects.Exceptions.NoValidPageException;
import com.sicknasty.objects.Page;
import com.sicknasty.objects.Post;
import com.sicknasty.persistence.exceptions.DBPostIDExistsException;
import com.sicknasty.persistence.exceptions.DBPostIDNotFoundException;

import java.util.ArrayList;

public interface PostPersistence {
    public enum FILTER_BY {TIME_CREATED, AMOUNT_LIKES, AMOUNT_DISLIKES};

    /**
     * Returns a Post specified by it's unique ID.
     * 
     * @param   id the unique ID of the Post
     * @return  the Post corresponding to the ID, otherwise it will return null
     * @throws  DBPostIDNotFoundException thrown when ID is not found
     */
    public Post getPostById(int id) throws DBPostIDNotFoundException, NoValidPageException;

    /**
     * Returns a specified number of Posts shared to a Page (this includes both personal and community Pages).
     * 
     * @param   page the Page to get all the posts from
     * @param   limit the maximum number of posts to fetch
     * @param   filter the filter to sort Page posts by
     * @param   accendingOrder set true to get an ArrayList sorted in accending order
     * @return  ArrayList of type Post (this can be an empty list)
     */
    public ArrayList<Post> getPostsByPage(Page page, int limit, FILTER_BY filter, boolean accendingOrder) throws NoValidPageException;

    /**
     * Inserts a new Post.
     *
     * @param   post the Post object to insert into the database
     * @throws  DBPostIDExistsException this gets thrown if you attempt to insert an existing post
     */
    public void insertNewPost(Post post) throws DBPostIDExistsException;

    /**
     * Deletes a Post specified by it's unique ID.
     * 
     * @param   id  the unique ID of the Post
     */
    public void deletePost(int id);

    /**
     * Deletes a Post specified by a Post object.
     * 
     * @param   post a Post object to delete
     */
    public void deletePost(Post post);

//    /**
//     * Fetches all comments that are under a Post
//     *
//     * @param   post the Post object to fetch comments from
//     * @param   limit the limit of posts to fetch. Set to 0 to get all comments
//     * @param   filter the filter to sort the ArrayList by
//     * @param   order whether the results get returned in ascending or descending order
//     * @return  returns an ArrayList of Comment objects sorted by filter
//     */
//    public ArrayList<Comment> getCommentsByPost(Post post, final int limit, FILTER_BY filter, boolean ascOrder);
//
//    /**
//     * Add a comment to a Post
//     *
//     * @param   comment the comment to save
//     */
//    public void addComment(Comment comment);
}