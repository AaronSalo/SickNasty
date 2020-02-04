package com.sicknasty.objects;

import java.util.ArrayList;

public class Community {
    private static int communityID = 0;
    private int id;
    private String name;
    private ArrayList<User> users;
    private Page communityPage;

    public Community(String name, User creator){
        communityID++;
        this.id = communityID;
        this.name = name;
        this.communityPage = new CommunityPage(name,this);
        this.users = new ArrayList<User>();
        users.add(creator);
    }

    public int getID(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public void addUser(User newUser){
        users.add(newUser);
    }

    public Page getPage(){
        return this.communityPage;
    }

    public void addPost(Post newPost){
        this.communityPage.addPost(newPost);
    }


}
