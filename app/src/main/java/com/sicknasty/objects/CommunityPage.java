package com.sicknasty.objects;

import java.util.ArrayList;

public class CommunityPage extends Page {

    private ArrayList<User> users; //list of users that are a part of this page

    public CommunityPage(String name, User creator){
        super(name, creator);
        this.users = new ArrayList<User>();
        users.add(creator);
    }


    public void addUser(User newUser){
        users.add(newUser);
    }

    
    /**
     * @param user  user we want to delete
     * @return  true on success, false on failure
     */
    public boolean deleteUser(User user) {
        return users.remove(user);
    }
}
