/*
abstract class for post
 */
package com.sicknasty.objects;

public abstract class Post {

    private String text;
    private static int globalpostID = 0;         //specific id for every post
    private User userId;//user id kept so we know which who owns this post
                                    //can discuss redundantcy later during refactoring, useful when posting to communities//so we can trace back to the users personal page.

    private String communityID;

    private Page pageId;            //stored so we know which page this post is being posted too
    private int thisPostID;

    private long timeCreated;
    private int likes;
    private int dislikes;

    public Post(){};

    public Post(String text, User userId, long timeCreated, int likes, int dislikes, Page page){
        globalpostID++;
        this.pageId = page;
        thisPostID = globalpostID;
        this.text = text;
        this.userId = userId;

        this.timeCreated = System.currentTimeMillis();
        this.likes = likes;
        this.dislikes = dislikes;
    }

    abstract void displayPost();

    public int getPostID() {
        return thisPostID;
    }

    public static int getGlobalpostID() {
        return globalpostID;
    }
    public Page getPageId(){
        return pageId;
    }

    public User getUserId(){
        return userId;
    }

    public String getText(){
        return text;
    }

    public void setText(String text){
        this.text = text;
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
