package com.sicknasty.objects;

import java.util.Date;

public class Comment {

    private String content;
    private User user;
    private Date timePosted;

    /*
    @userCommenting the user that is commenting
    @content the comments' content (just a string)
     */
    public Comment (User userCommenting, String content) {
        this.user = userCommenting;
        this.content = content;
        timePosted = new Date(System.currentTimeMillis());
    }

    public String getContent() { return content; }

    public User getUser() { return user; }

    public Date getTimePosted() { return timePosted; }

}
