package com.example.test1;

import java.util.ArrayList;

public abstract class Page {
    private static int pageID = 0;
    private int id;
    private String pageName;
    private ArrayList<Post> postList;

    public Page(String pageName){
        pageID++;
        this.id = pageID;
        this.pageName = pageName;
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
