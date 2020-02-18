//object for only holding picture posts
package com.sicknasty.objects;

public class PicturePost extends Post {



    //private String examplePic;

    private int PICTURE_PATH;

   public PicturePost(String text, User user, int path, long timeCreated, int likes, int dislikes, Page page){      //uses superclass constructor to implement tital and user object
       super(text, user, timeCreated, likes, dislikes, page);
      // examplePic = pic;
       PICTURE_PATH = path;
   }

    @Override
    void displayPost() {                //UI work

    }

    public int getPICTURE_PATH(){
       return this.PICTURE_PATH;
    }



}
