package com.sicknasty.objects;

import java.util.ArrayList;

public class PersonalPage extends Page {

    private ArrayList<Post> posts;
    private User user;

    public PersonalPage(String name, User creator){
        super(name, creator);
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}