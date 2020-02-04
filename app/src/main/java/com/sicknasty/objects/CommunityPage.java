package com.sicknasty.objects;

public class CommunityPage extends Page {
    private Community community;

    public CommunityPage(String name, Community community){
        super(name);
        this.community = community;
    }

    public Community getCommunity() {
        return community;
    }
}
