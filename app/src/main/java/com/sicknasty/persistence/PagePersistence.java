package com.sicknasty.persistence;

import com.sicknasty.objects.Page;
import com.sicknasty.objects.User;

public interface PagePersistence {
    /**
     * Returns a Page specified by it's unique name.
     * 
     * @param   name    the unique name of the Page
     * @return      the Page corresponding to the ID, otherwise it will return null
     */
    public Page getPage(String name);

    /**
     * Inserts a new Page.
     * 
     * @return      returns true on success, otherwise return false
     */
    public boolean insertNewPage(Page page);

    /**
     * Delete a Page by its unique name.
     *
     * @param   name  the unique name of the Page
     * @return      returns true if it deleted successfully, otherwise false
     */
    public boolean deletePage(String name);

    /**
     * Delete a Page by object.
     *
     * @param   page    the page object to delete
     * @return          returns true if it deleted successfully, otherwise false
     */
    public boolean deletePage(Page page);

    /**
     * Adds a follower to a page.
     *
     * @param   page the page to add the follower to
     * @param   user the user to add
     * @return  returns true if added successfully, otherwise false
     */
    public boolean addFollower(Page page, User user);
}