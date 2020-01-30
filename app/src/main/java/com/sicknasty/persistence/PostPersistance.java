/**
 * TWO THINGS:
 *      - GetPostsByUser() and GetPostsByCommunity() can realistically be combined together if we consider searching by Page (not User or subclass of Page)
 *      - the get posts via User/Community will (should) have some sort of "filter" like most recent/oldest/most likes/least likes
 */

package com.sicknasty.persistance;

import java.util.ArrayList;

public interface PostPersistance {
    /**
     * Returns a Post specified by it's unique ID.
     * 
     * @param   id  the unique ID of the Post
     * @return      the Post corresponding to the ID, otherwise it will return null
     */
    public Post GetPostById(int id);

    /**
     * Returns a specified number of Posts created by a User
     * 
     * @param   user    the User to get all the posts from
     * @param   limit   the maximum number of posts to fetch
     * @return          ArrayList of type Post
     */
    public ArrayList<Post> GetPostsByUser(User user, int limit);

    /**
     * Returns a specified number of Posts shared to a Community
     * 
     * @param   community the Community to get all the posts from
     * @param   limit   the maximum number of posts to fetch
     * @return          ArrayList of type Post
     */
    public ArrayList<Post> GetPostsByCommunity(Page community, int limit);

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
     * @param post  a Post object to delete
     * @return      returns true on success, otherwise return false
     */
    public boolean DeletePost(Post post);
}