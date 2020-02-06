package com.sicknasty.application;

import com.sicknasty.persistence.*;
import com.sicknasty.persistence.stubs.*;


/*******
 * @author aaron
 *
 * this class manages the database, and allows us to fetch the db from other classes with ease
 * We create a new data handler for user, page, and post, which hold the data for each respective
 * items. We then can ask this class to provide the data handlers for each, allowing for persistant
 * data
 */
public class Service {

    private static UserPersistence userData = null;
    private static PostPersistence postData = null;
    private static PagePersistence pageData = null;

    //this will retrieve the user stub, from which we can call functs such as getUser()
    public static synchronized UserPersistence getUserData() {
        //if the stub hasn't been created yet, create it
        if(userData == null) {
            userData = new UserPersistenceStub();
        }
        return userData;
    }

    public static synchronized PostPersistence getPostData() {
        if(postData == null) {
            postData = new PostPersistenceStub();
        }
        return postData;
    }

    public static synchronized PagePersistence getPageData() {
        if(pageData == null) {
            pageData = new PagePersistenceStub();
        }
        return pageData;
    }
}
