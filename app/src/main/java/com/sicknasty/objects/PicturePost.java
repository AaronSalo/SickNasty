//object for only holding picture posts
package com.sicknasty.objects;

import android.graphics.Bitmap;

import java.util.BitSet;

public class PicturePost extends Post {

    private int PICTURE_PATH;
    private Bitmap bm;
    public PicturePost(String text, User user, int path, long timeCreated, int likes, int dislikes, Page page){      //uses superclass constructor to implement tital and user object
       super(text, user, timeCreated, likes, dislikes, page);
       PICTURE_PATH =path;
    }

    public int getPICTURE_PATH(){
       return this.PICTURE_PATH;
    }

    public void setBm(Bitmap bm) {
        this.bm = bm;
    }

    public Bitmap getBm() {
        return bm;
    }
}
