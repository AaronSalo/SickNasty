package com.sicknasty.objects;

import java.util.ArrayList;

public class PersonalPage extends Page {

    private ArrayList<PicturePost> posts;
    private User user;

    public PersonalPage(User creator){
        super(creator.getUsername(), creator);
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public ArrayList<PicturePost> getPosts() {
        return posts;
    }
}