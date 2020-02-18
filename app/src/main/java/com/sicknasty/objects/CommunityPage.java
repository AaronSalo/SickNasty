package com.sicknasty.objects;

import java.util.ArrayList;

public class CommunityPage extends Page {

    private ArrayList<User> users;  //list of users that are a part of this page
    private ArrayList<User> mods;   //list of people who control of the page
    private ArrayList<User> bannedUsers;
    private String comName;

    public CommunityPage(String name, User creator){
        super(creator);
        comName = name;
        bannedUsers = new ArrayList<>();
        mods = new ArrayList<>();
        users = new ArrayList<>();
        this.users = new ArrayList<>();
        users.add(creator);

    }
    public boolean addUser(User newUser){      //adds a new user that follows the page, true if follows, false if not
                                                //checks banned users arraylist to make sure they cant follow again
        if(!bannedUsers.contains(newUser)) {
            users.add(newUser);
            return true;
        }
        return false;
    }

    public void addMod(User newMod){        //add a mod to mod user array list that can make changes to page
                                            // only mods should have this power
        mods.add(newMod);
    }

    public String getComName(){         //gets communities name
        return comName;
    }

    public void banUser(User user) {     //deletes user from page and adds them to banned array list so they can follow again
        if(!bannedUsers.contains(user)){
            bannedUsers.add(user);
            users.remove(user);
                    //also remove from user's list
        }
    }

}
