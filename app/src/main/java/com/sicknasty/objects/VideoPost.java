//video post object,, that holds videos for media type
package com.sicknasty.objects;

public class VideoPost extends Post {

    public VideoPost(String insertedText, User user,String path, long timeCreated, int likes, int dislikes, Page page){      //uses superclass constructor to implement tital and user object
        super(insertedText, user, path, likes, dislikes, page);
    }
}
