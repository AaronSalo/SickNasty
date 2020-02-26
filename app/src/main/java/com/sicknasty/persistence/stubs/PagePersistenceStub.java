package com.sicknasty.persistence.stubs;

import java.util.ArrayList;
import java.util.HashMap;

import com.sicknasty.objects.User;
import com.sicknasty.persistence.PagePersistence;
import com.sicknasty.objects.Page;

public class PagePersistenceStub implements PagePersistence {
    private HashMap<String, Page> pages;
    private HashMap<String, ArrayList<User>> followers;

    public PagePersistenceStub() {
        this.pages = new HashMap<String, Page>();
        this.followers = new HashMap<String, ArrayList<User>>();
    }

    @Override
    public Page getPage(String name) {
        if (name == null) return null;

        return this.pages.get(name);
    }

    @Override
    public boolean insertNewPage(Page page) {
        if (page == null) return false;

        String pageName = page.getPageName();

        if (pageName == null) return false;

        if (pages.containsKey(pageName)) return false;

        Page localPage = this.pages.get(page.getPageID());

        if (localPage == null) {
            this.pages.put(pageName, page);

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

    @Override
    public boolean addFollower(Page page, User user) {
        if (page == null) return false;

        ArrayList<User> localFollowers = this.followers.get(page.getPageID());

        if (localFollowers == null) {
            localFollowers = new ArrayList<User>();

            this.followers.put(page.getPageName(), localFollowers);
        }

        if (localFollowers.contains(user)) return false;

        localFollowers.add(user);

        return true;
    }
}