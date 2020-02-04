package com.sicknasty.application;

import com.sicknasty.persistence.stubs.*;

public class Service {

    private static UserPersistenceStub userData = null;
    private static PostPersistenceStub postData = null;
    private static PagePersistenceStub pageData = null;

    public static synchronized UserPersistenceStub getUserData() {
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
