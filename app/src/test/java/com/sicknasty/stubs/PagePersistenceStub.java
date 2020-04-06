package com.sicknasty.stubs;

import java.util.ArrayList;
import java.util.HashMap;

import com.sicknasty.objects.User;
import com.sicknasty.persistence.PagePersistence;
import com.sicknasty.objects.Page;
import com.sicknasty.persistence.exceptions.DBPageNameExistsException;
import com.sicknasty.persistence.exceptions.DBPageNameNotFoundException;
import com.sicknasty.persistence.exceptions.DBUserAlreadyFollowingException;

public class PagePersistenceStub implements PagePersistence {
    private HashMap<String, Page> pages;
    private HashMap<String, ArrayList<User>> followers;

    public PagePersistenceStub() {
        this.pages = new HashMap<String, Page>();
        this.followers = new HashMap<String, ArrayList<User>>();
    }

    @Override
    public Page getPage(String name) throws DBPageNameNotFoundException {
        if (name == null) return null;

        if (!this.pages.containsKey(name)) throw new DBPageNameNotFoundException(name);

        return this.pages.get(name);
    }

    @Override
    public void insertNewPage(Page page) throws DBPageNameExistsException {
        String pageName = page.getPageName();

        if (pages.containsKey(pageName)) throw new DBPageNameExistsException(page.getPageName());

        this.pages.put(pageName, page);
    }

    @Override
    public void deletePage(String name) {
        this.pages.remove(name);
    }

    @Override
    public void deletePage(Page page) {
        Page existingPost = this.pages.get(page.getPageName());

        if (existingPost != null) {
            this.deletePage(existingPost.getPageName());
        }
    }

    @Override
    public void addFollower(Page page, User user) throws DBUserAlreadyFollowingException {
        ArrayList<User> localFollowers = this.followers.get(page.getPageID());

        if (localFollowers == null) {
            localFollowers = new ArrayList<User>();

            this.followers.put(page.getPageName(), localFollowers);
        }

        if (localFollowers.contains(user))
            throw new DBUserAlreadyFollowingException(user.getUsername(), page.getPageName());
        page.addFollower(user);
        //lucas can you update this ,i don't know where to update
        //the test follow in unit test will pass
        //go to accesspagestTest and comment out line 103 in testFollowerTOPage
        localFollowers.add(user);
    }

	@Override
	public void changeName(String oldName, String newName) {
		if (this.pages.containsKey(oldName)) {
			Page oldPage = this.pages.get(oldName);
			oldPage.changePageName(newName);

			this.pages.remove(oldName);	
			this.pages.put(newName, oldPage);
		}
	}

    @Override
    public ArrayList<String> getAllCommunityPageNames() {
        ArrayList<String> returnResult = new ArrayList<String>();

        for (String name : this.pages.keySet()) {
            returnResult.add(name);
        }

        return returnResult;
    }
}
