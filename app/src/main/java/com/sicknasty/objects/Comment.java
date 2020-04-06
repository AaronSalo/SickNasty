package com.sicknasty.objects;

import java.util.Date;

public class Comment {

    private String content;
    private User user;
    private int postId;
    private long timePosted;

    /**
     * @param userCommenting - the user commenting
     * @param content - the content of the comment
     * @param postId - the id the comment is on
     */
    public Comment (User userCommenting, String content, int postId) {
        this.user = userCommenting;
        this.content = content;
        this.postId = postId;
        timePosted = System.currentTimeMillis();
    }

    public int getPostId(){ return postId; }

    public String getContent() { return content; }

    public User getUser() { return user; }

    public long getTimePosted() { return timePosted; }

}
