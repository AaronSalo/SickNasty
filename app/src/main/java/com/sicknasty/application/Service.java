package com.sicknasty.application;

import com.sicknasty.persistence.stubs.*;


/*******
 * @author aaron
 *
 * this class manages the database, and allows us to fetch the db from other classes with ease
 */
public class Service {

    private static UserPersistenceStub userData = null;
    private static PostPersistenceStub postData = null;
    private static PagePersistenceStub pageData = null;

    //this will retrieve the user stub, from which we can call functs such as getUser()
    public static synchronized UserPersistenceStub getUserData() {
        //if the stub hasn't been created yet, create it
        if(userData == null) {
            userData = new UserPersistenceStub();
        }
        return userData;
    }

    public static synchronized PostPersistenceStub getPostData() {
        if(postData == null) {
            postData = new PostPersistenceStub();
        }
        return postData;
    }

    public static synchronized PagePersistenceStub getPageData() {
        if(pageData == null) {
            pageData = new PagePersistenceStub();
        }
        return pageData;
    }
}
