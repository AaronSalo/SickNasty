package com.sicknasty.persistence;

import com.sicknasty.objects.Page;
import com.sicknasty.objects.User;
import com.sicknasty.persistence.exceptions.DBPageNameExistsException;
import com.sicknasty.persistence.exceptions.DBPageNameNotFoundException;
import com.sicknasty.persistence.exceptions.DBUserAlreadyFollowingException;

import java.util.ArrayList;

public interface PagePersistence {
    /**
     * Returns a Page specified by it's unique name.
     *
     * @param name the unique name of the Page
     * @return the Page corresponding to the ID, otherwise it will return null
     * @throws DBPageNameNotFoundException thrown when Page name not found
     */
    public Page getPage(String name) throws DBPageNameNotFoundException;

    /**
     * Inserts a new Page.
     *
     * @param page  the Page object
     * @throws DBPageNameExistsException thrown when page already exists
     */
    public void insertNewPage(Page page) throws DBPageNameExistsException;

    /**
     * Delete a Page by its unique name.
     *
     * @param name the unique name of the Page
     */
    public void deletePage(String name);

    /**
     * Delete a Page by object.
     *
     * @param page the page object to delete
     */
    public void deletePage(Page page);

    /**
     * Adds a follower to a page.
     *
     * @param page the page to add the follower to
     * @param user the user to add
     * @throws DBUserAlreadyFollowingException thrown when User already follows the Page
     */
    public void addFollower(Page page, User user) throws DBUserAlreadyFollowingException;

    /**
     * This changes the name of the page.
     *
     * @param    oldName the old name of the page to change
     * @param    newName the new name of the page to set it to
     */
    public void changeName(String oldName, String newName);

    /**
     * Get the names of all the Community Pages that exist
     *
     * @return returns unsorted ArrayList of page names
     */
    public ArrayList<String> getAllCommunityPageNames();
}