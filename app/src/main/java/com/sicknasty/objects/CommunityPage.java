package com.sicknasty.objects;

import java.util.ArrayList;

public class CommunityPage extends Page {

    private ArrayList<User> users; //list of users that are a part of this page
    private ArrayList<User> mods;   //list of people who control of the page
    private ArrayList<User> bannedUsers;
    private ArrayList<Post> posts;
    private String comName;

    public CommunityPage(String name, User creator){
        super(creator);
        comName = name;
        posts = new ArrayList<Post>();
        bannedUsers = new ArrayList<User>();
        mods = new ArrayList<User>();
        this.users = new ArrayList<User>();
        users.add(creator);

    }

    public void addPost (Post newPost){     //add post to community page
        posts.add(newPost);
    }

    public boolean deletePost(Post toDelete){      //delete post and return true if it happens, false it not
        return posts.remove(toDelete);

    }

    public boolean addUser(User newUser){      //adds a new user that follows the page, true if follows, false if not
                                                //checks banned users arraylist to make sure they cant follow again
        boolean returnVal = false;

        if(!bannedUsers.contains(newUser)) {
            returnVal = true;
            return users.add(newUser);
        }
        return returnVal;
    }

    public void addMod(User newMod){        //add a mod to mod user array list that can make changes to page
                                            //only mods should have this power
        mods.add(newMod);
    }

    public String getComName(){         //gets communities name
        return comName;
    }

    public boolean banUser(User user) {     //deletes user from page and adds them to banned array list so they can follow again
        bannedUsers.add(user);
        return users.remove(user);
    }


    /**
     * @param user  user we want to delete
     * @return  true on success, false on failure
     */
    public boolean deleteUser(User user) {
        return users.remove(user);
    }




}
