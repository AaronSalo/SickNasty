//object for only holding picture posts
package com.sicknasty.objects;

public class PicturePost extends Post {



    String examplePic;

    String PICTURE_PATH="";


   public PicturePost(String insertedText, User user,String pic, String path){      //uses superclass constructor to implement tital and user object
       super(insertedText, user);
       examplePic = pic;
       PICTURE_PATH = path;
   }

    @Override
    void displayPost() {                //UI work

    }

}
