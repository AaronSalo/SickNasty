package com.sicknasty.objects;

import com.sicknasty.objects.Exceptions.PageNameException;

import java.util.ArrayList;

public abstract class Page {
    private static int pageID = 0;
    private int id;
    private String pageName;                //what is pageName vs Community name?
    private ArrayList<Post> postList;
    private User creator;               //user that created the page


    private ArrayList<User> followers;
                                        //decide whether to keep creator in Page or in subclass
    public Page(User creator) throws PageNameException{
        this(creator,creator.getUsername());
        pageID++;
        this.id = pageID;
    }

    public Page(User creator, String name) throws PageNameException {
        //this will change when i refactor whole code on saturday or sunday(let it be hardcoded for now)
        if(name.length() < 3){
            throw new PageNameException("Page Name cannot be less than 3 characters");
        }
        else if(name.length()  > 12){
            throw new PageNameException("Page name cannot be more than 12 characters");
        }
        else if(creator!=null){
            followers=new ArrayList<>();
            this.creator =  creator;
            this.pageName = name;
            followers.add(creator);
            postList=new ArrayList<>();
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

    public void changePageName(String newName) {this.pageName = newName; }

    //package private
    ArrayList<User> getFollowers() {
        return followers;
    }

    public ArrayList<Post> getPostList(){
        return postList;
    }
}
