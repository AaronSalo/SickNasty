package com.sicknasty.objects;

import com.sicknasty.objects.Exceptions.InvalidCommunityPageNameException;

import java.util.ArrayList;

public abstract class Page {
    private static int pageID = 0;
    private int id;
    private String pageName;                //what is pageName vs Community name?
    private ArrayList<Post> postList;
    private User creator;               //user that created the page


    private ArrayList<User> followers;
                                        //decide whether to keep creator in Page or in subclass
    public Page(User creator) {
        pageID++;
        this.id = pageID;

        if (creator != null){
            this.creator = creator;

            followers = new ArrayList<>();
            pageName = creator.getUsername();
            followers.add(creator);
            postList = new ArrayList<>();
        }
    }

    public Page(User creator, String name) throws InvalidCommunityPageNameException {
        this(creator);
        setPageName(name);
    }

    public void setPageName(String pageName) throws InvalidCommunityPageNameException {
        if (pageName.length() < 3){
            throw new InvalidCommunityPageNameException("Page Name cannot be less than 3 characters");              //this will change when i refactor whole code on saturday or sunday(let it be hardcoded for now)
        } else if (pageName.length() > 12){
            throw new InvalidCommunityPageNameException("Page name cannot be more than 12 characters");
        } else {
            this.pageName = pageName;
        }
    }

    public User getCreator() {
        return creator;
    }

    public int getPageID(){
        return this.id;
    }

    public String getPageName(){
        return this.pageName;
    }

    public void changePageName(String newName){
        pageName = newName;
    }

    //package private
    ArrayList<User> getFollowers() {
        return followers;
    }

    public ArrayList<Post> getPostList(){
        return postList;
    }
}
