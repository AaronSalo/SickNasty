package com.sicknasty.objects;

public class CommunityPage extends Page {
    private String pageName;

    public CommunityPage(User creator, String name) {
        super(creator,name);
    }

    public CommunityPage(String name) {
        super(name);
    }
}
