package com.sicknasty.persistence.stubs;

import java.util.HashMap;

import com.sicknasty.persistence.PagePersistence;

public class PagePersistenceStub implements PagePersistence {
    private HashMap<Integer, Page> pages;

    public PagePersistenceStub() {
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