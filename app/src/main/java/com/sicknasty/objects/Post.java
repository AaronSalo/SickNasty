/*
abstract class for post
 */


public abstract class Post {

    private String title;
    private static int globalpostID = 0;         //specific id for every post
    private User userId;            //user id to see who this post belongs to
    private int thisPostID;


    public Post(){};

    public Post(String insertedText, User userId){
        this.globalpostID = globalpostID++;
        thisPostID = globalpostID;
        this.title = insertedText;
        this.userId = userId;

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

}
