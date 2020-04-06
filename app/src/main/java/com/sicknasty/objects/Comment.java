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

    @Override
    public boolean equals(Object obj) {
        Comment otherComment = (Comment) obj;

        String thisCommentUsername = this.user.getUsername();
        String otherCommentUsername = otherComment.getUser().getUsername();

        String otherContent = otherComment.getContent();

        int otherPostID = otherComment.getPostId();

        return thisCommentUsername.equals(otherCommentUsername) &&
                this.content.equals(otherContent) &&
                this.postId == otherPostID;
    }
}
