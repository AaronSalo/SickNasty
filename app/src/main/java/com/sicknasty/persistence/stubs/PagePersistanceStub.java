package com.sicknasty.persistance.stubs;

import java.util.HashMap;

import com.sicknasty.persistance.PagePersistance;

public class PagePersistanceStub implements PagePersistance {
    private HashMap<Integer, Page> pages;

    public PagePersistanceStub() {
        this.pages = new HashMap<Integer, Page>();
    }

    @Override
    public Page GetPageById(int id) {
        return this.pages.get(id);
    }

    @Override
    public boolean InsertNewPage() {
        // waiting on Page implementation
        return false;
    }
}