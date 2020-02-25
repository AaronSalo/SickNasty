//class for text posts only. no media added
package com.sicknasty.objects;

public class TextPost extends Post{
    public TextPost(String insertedText, User user,String path, int likes, int dislikes, Page page){      //uses superclass constructor to implement tital and user object
        super(insertedText, user,path, likes, dislikes, page);
    }
}
