package com.sicknasty.persistence;

public interface PagePersistence {
    /**
     * Returns a Page specified by it's unique ID.
     * 
     * @param   id  the ID of the Page
     * @return      the Page corresponding to the ID, otherwise it will return null
     */
    public Page GetPageById(int id);

    /**
     * Inserts a new Page.
     * 
     * @return      returns true on sucess, otherwise return false
     */
    public boolean InsertNewPage();
}