package com.example.test1;

import java.util.ArrayList;

public class PersonalPage extends Page {
    ArrayList Post posts;

    private ArrayList<Post> posts;
    private User user;

    public PersonalPage(String name, User user){
        super(name);
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}