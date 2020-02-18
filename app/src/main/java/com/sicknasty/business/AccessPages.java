package com.sicknasty.business;

import com.sicknasty.application.Service;
import com.sicknasty.objects.Page;
import com.sicknasty.persistence.PagePersistence;
import com.sicknasty.persistence.stubs.PagePersistenceStub;

/** @author aaron
 * wrapper for the page db
 * passes info from UI to the db and vise versa
 */

public class AccessPages {

    private PagePersistence pageHandler;
//    private Page page;

    public AccessPages() {
        pageHandler = Service.getPageData();
    }

    /**
     * @param pageName name of the page we want to get
     * @return the page, or null if not found
     */
    public Page getPage(String pageName) {
        return pageHandler.getPage(pageName);
    }

    /**
     * Inserts a new Page.
     *
     * @return      returns true on success, otherwise return false
     */
    public boolean insertNewPage(Page page){
        return pageHandler.insertNewPage(page);
    }

    /**
     * Delete a Page by its unique name.
     *
     * @param   name  the unique name of the Page
     * @return      returns true if it deleted successfully, otherwise false
     */
    public boolean deletePage(String name){
        return pageHandler.deletePage(name);
    }

}//end of class
