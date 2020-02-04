/*
abstract class for post
 */
package com.sicknasty.objects;

public abstract class Post {

    private String title;
    private static int globalpostID = 0;         //specific id for every post
    private User userId;            //user id to see who this post belongs to
    private int thisPostID;

    private long timeCreated;
    private int likes;
    private int dislikes;


    public Post(){};

    public Post(String insertedText, User userId, long timeCreated, int likes, int dislikes){
        this.globalpostID = globalpostID++;
        thisPostID = globalpostID;
        this.title = insertedText;
        this.userId = userId;

        this.timeCreated = System.currentTimeMillis();
        this.likes = likes;
        this.dislikes = dislikes;
    }

    abstract void displayPost();

    public int getPostID() {
        return thisPostID;
    }

    public User getUserId(){
        return userId;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String insertedText){
        this.title = insertedText;
    }

    public long getTimeCreated() {
        return this.timeCreated;
    }

    public int getNumberOfLikes() {
        return this.likes;
    }

    public int getNumberOfDislikes() {
        return this.dislikes;
    }
}
