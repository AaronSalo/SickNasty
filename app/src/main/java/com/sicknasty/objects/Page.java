package com.sicknasty.objects;

import java.util.ArrayList;

public abstract class Page {
    private static int pageID = 0;
    private int id;
    private String pageName;
    private ArrayList<Post> postList;
    private User creator; //user that created the page

    public Page(String pageName, User creator){
        pageID++;
        this.id = pageID;
        this.pageName = pageName;
        this.creator = creator;
    }

    public void setPostList(ArrayList<Post> postList){
        this.postList = postList;
    }

    public int getPageID(){
        return this.id;
    }

    public String getPageName(){
        return this.pageName;
    }

    public void showPostList(){
        if(postList != null){
            for(Post p: postList){
                //p.showPost;
            }
        }
    }

    public void addPost(Post newPost){
        if(postList != null && newPost!= null){
            postList.add(newPost);
        }
    }

    public void deletePost(){}


}
