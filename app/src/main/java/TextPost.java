//class for text posts only. no media added
public class TextPost extends Post{


    public TextPost(String insertedText, User user){      //uses superclass constructor to implement tital and user object

        super(insertedText, user);

    }


    @Override
    void displayPost() {

    }
}
