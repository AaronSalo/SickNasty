
package com.sicknasty.objects;

import com.sicknasty.objects.Exceptions.CaptionTextException;
import com.sicknasty.objects.Exceptions.NoValidPageException;

import java.util.ArrayList;

public class Post {

    private String text;
    private String path;

    private ArrayList<Comment> comments;

    private User userId;                        //user id kept so we know which who owns this post
    private Page pageId;            //stored so we know which page this post is being posted too

    private int postID = -1;
    private int likes;
    private int dislikes;

    private boolean liked;

    private long timeCreated;

    public Post(String text, User userId, String path, int likes, int dislikes, Page page) throws NoValidPageException, CaptionTextException {
        if(page != null)
            this.pageId = page;
        else
            throw new NoValidPageException("Could not find a page to post to");
            
        this.comments = new ArrayList<Comment>();

        if(text.length() > Constants.POST_MAX_CONTENT_LENGTH)
            throw new CaptionTextException("Caption is too long; No more than " + Constants.POST_MAX_CONTENT_LENGTH + " characters");
        else{
            this.text = text;
        }
        
        this.userId = userId;
        this.path=path;
        this.timeCreated = System.currentTimeMillis();
        this.likes = likes;
        this.dislikes = dislikes;
        liked = false;
    }

    public Page getPageId() {
        return pageId;
    }

    public int getPostID() {
        return postID;
    }

    public User getUserId(){
        return userId;
    }

    public String getText(){
        return text;
    }

    public String getPath() {
        return path;
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

    public void setText(String text){
        this.text = text;
    }

    public void setTimeCreated(long time) {
        this.timeCreated = time;
    }

    public void setPostID(int id) {
        this.postID = id;
    }

    public ArrayList<Comment> getComments() { return comments; }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

}
