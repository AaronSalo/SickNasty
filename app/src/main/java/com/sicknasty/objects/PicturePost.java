//object for only holding picture posts
package com.sicknasty.objects;

public class PicturePost extends Post {

    public PicturePost(String text, User user, String  path,int likes, int dislikes, Page page){      //uses superclass constructor to implement tital and user object
       super(text, user, path, likes, dislikes, page);
    }
}
