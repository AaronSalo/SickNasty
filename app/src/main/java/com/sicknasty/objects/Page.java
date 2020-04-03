package com.sicknasty.objects;

import com.sicknasty.objects.Exceptions.InvalidPageNameException;

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
        if(creator!=null){
            followers=new ArrayList<>();
            this.creator =  creator;
            this.pageName = creator.getUsername();
            followers.add(creator);
            postList=new ArrayList<>();
        }
    }

    public Page(User creator, String name) throws InvalidPageNameException {
        //this will change when i refactor whole code on saturday or sunday(let it be hardcoded for now)
        if(name.length() < 3){
            throw new InvalidPageNameException("Page Name cannot be less than 3 characters");
        }
        else if(name.length()  > 12){
            throw new InvalidPageNameException("Page name cannot be more than 12 characters");
        }
        else if(creator!=null){
            followers=new ArrayList<>();
            this.creator =  creator;
            this.pageName = name;
            followers.add(creator);
            postList=new ArrayList<>();         //this is violating dry i know  but it works unless you have some suggestion -Jay
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
