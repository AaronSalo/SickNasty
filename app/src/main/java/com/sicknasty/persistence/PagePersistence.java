package com.sicknasty.persistence;

import com.sicknasty.objects.Page;

public interface PagePersistence {
    /**
     * Returns a Page specified by it's unique name.
     * 
     * @param   name    the unique name of the Page
     * @return      the Page corresponding to the ID, otherwise it will return null
     */
    public Page GetPage(String name);

    /**
     * Inserts a new Page.
     * 
     * @return      returns true on success, otherwise return false
     */
    public boolean InsertNewPage(Page page);

    /**
     * Delete a Page by its unique name.
     *
     * @param   name  the unique name of the Page
     * @return      returns true if it deleted successfully, otherwise false
     */
    public boolean DeletePage(String name);

    /**
     * Delete a Page by object.
     *
     * @param   page    the page object to delete
     * @return          returns true if it deleted successfully, otherwise false
     */
    public boolean DeletePage(Page page);
}