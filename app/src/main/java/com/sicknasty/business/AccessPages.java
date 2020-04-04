package com.sicknasty.business;

import com.sicknasty.application.Service;
import com.sicknasty.objects.Page;
import com.sicknasty.objects.User;
import com.sicknasty.persistence.PagePersistence;
import com.sicknasty.persistence.exceptions.DBPageNameExistsException;
import com.sicknasty.persistence.exceptions.DBPageNameNotFoundException;
import com.sicknasty.persistence.exceptions.DBUserAlreadyFollowingException;

/** @author aaron
 * wrapper for the page db
 * passes info from UI to the db and vise versa
 */

public class AccessPages {

    private PagePersistence pageHandler;

    /**
     * this constructor is to get the hsql db
     *
     */
    public AccessPages() {
        pageHandler = Service.getPageData();
    }

    /**this is for Unit Test
      *this is for unit test with fake database / stub
       *test are performed through business layer
       *-Jay
     */
    public AccessPages(final PagePersistence pagePersistence){ pageHandler = pagePersistence; }

    /**
     * @param pageName name of the page we want to get
     * @return the page, or null if not found
     */
    public Page getPage(String pageName) throws DBPageNameNotFoundException {
        return pageHandler.getPage(pageName);
    }

    /**
     * Inserts a new Page.
     */
    public void insertNewPage(Page page) throws DBPageNameExistsException {
        pageHandler.insertNewPage(page);
    }

    /**
     * Delete a Page by its unique name.
     *
     * @param   name  the unique name of the Page
     */
    public void deletePage(String name){
        pageHandler.deletePage(name);
    }


    /**
     * Add a follower to the user's page
     * @param user that wants to follow the page
     * @param page page being followed by User
     */
    public void addFollower(Page page, User user) throws DBUserAlreadyFollowingException {
        pageHandler.addFollower(page, user);
    }

}//end of class
