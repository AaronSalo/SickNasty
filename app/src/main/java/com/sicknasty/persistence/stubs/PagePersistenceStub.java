package com.sicknasty.persistence.stubs;

import java.util.HashMap;

import com.sicknasty.persistence.PagePersistence;
import com.sicknasty.objects.Page;

public class PagePersistenceStub implements PagePersistence {
    private HashMap<String, Page> pages;

    public PagePersistenceStub() {
        this.pages = new HashMap<String, Page>();
    }

    @Override
    public Page GetPage(String name) {
        return this.pages.get(name);
    }

    @Override
    public boolean InsertNewPage(Page page) {
        Page localPage = this.pages.get(page.getPageID());

        if (localPage == null) {
            this.pages.put(page.getPageName(), page);

            return true;
        }

        return false;
    }

    @Override
    public boolean DeletePage(String name) {
        Page result = this.pages.remove(name);

        return result != null;
    }

    @Override
    public boolean DeletePage(Page page) {
        Page exisitingPost = this.pages.get(page.getPageName());

        if (exisitingPost == null) {
            return false;
        }

        // i dont like this.
        // the existance of this function can be discussed
        return this.DeletePage(exisitingPost.getPageName());
    }
}