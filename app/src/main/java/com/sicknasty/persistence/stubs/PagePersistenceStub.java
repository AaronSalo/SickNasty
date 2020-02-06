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
    public Page getPage(String name) {
        if (name == null) return null;

        return this.pages.get(name);
    }

    @Override
    public boolean insertNewPage(Page page) {
        if (page == null) return false;

        Page localPage = this.pages.get(page.getPageID());

        if (localPage == null) {
            this.pages.put(page.getPageName(), page);

            return true;
        }

        return false;
    }

    @Override
    public boolean deletePage(String name) {
        if (name == null) return false;

        Page result = this.pages.remove(name);

        return result != null;
    }

    @Override
    public boolean deletePage(Page page) {
        if (page == null) return false;

        Page exisitingPost = this.pages.get(page.getPageName());

        if (exisitingPost == null) {
            return false;
        }

        // i dont like this.
        // the existance of this function can be discussed
        return this.deletePage(exisitingPost.getPageName());
    }
}