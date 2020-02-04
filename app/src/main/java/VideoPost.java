//video post object,, that holds videos for media type
public class VideoPost extends Post {


    int exampleVid;
    String VIDEO_PATH = "";    //EXAMPLE OF PATH

    public VideoPost(String insertedText, User user,int vidGoesHere, String path){      //uses superclass constructor to implement tital and user object
        super(insertedText, user);
        exampleVid = vidGoesHere;
        VIDEO_PATH = path;
    }

    @Override
    void displayPost() {

    }


}
