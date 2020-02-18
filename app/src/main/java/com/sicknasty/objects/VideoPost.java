//video post object,, that holds videos for media type
package com.sicknasty.objects;

public class VideoPost extends Post {


    int exampleVid;
    String VIDEO_PATH = "";    //EXAMPLE OF PATH

    public VideoPost(String insertedText, User user,int vidGoesHere, String path, long timeCreated, int likes, int dislikes, Page page){      //uses superclass constructor to implement tital and user object
        super(insertedText, user, timeCreated, likes, dislikes, page);
        exampleVid = vidGoesHere;
        VIDEO_PATH = path;
    }

    @Override
    void displayPost() {

    }


}
