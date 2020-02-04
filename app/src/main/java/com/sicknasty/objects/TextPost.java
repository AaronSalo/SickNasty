//class for text posts only. no media added
package com.sicknasty.objects;

public class TextPost extends Post{


    public TextPost(String insertedText, User user, long timeCreated, int likes, int dislikes){      //uses superclass constructor to implement tital and user object

        super(insertedText, user, timeCreated, likes, dislikes);

    }


    @Override
    void displayPost() {

    }
}
