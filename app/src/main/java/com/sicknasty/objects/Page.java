package com.sicknasty.objects;

import java.util.ArrayList;

public abstract class Page {
    private static int pageID = 0;
    private int id;
    private String pageName;
    private ArrayList<Post> postList;
    private User creator;                   //user that created the page

    public Page(User creator){
        pageID++;
        this.id = pageID;
        if(creator!=null){
            this.creator = creator;
            this.pageName = creator.getUsername();
        }
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
