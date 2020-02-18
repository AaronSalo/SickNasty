package com.sicknasty.application;

import com.sicknasty.R;
import com.sicknasty.objects.PicturePost;
import com.sicknasty.objects.Post;
import com.sicknasty.objects.User;
import com.sicknasty.persistence.*;
import com.sicknasty.persistence.stubs.*;


/*******
 * @author aaron
 *
 * this class manages the database, and allows us to fetch the db from other classes with ease
 * We create a new data handler for user, page, and post, which hold the data for each respective
 * items. We then can ask this class to provide the data handlers for each, allowing for persistant
 * data
 */
public class Service {

    private static UserPersistence userData = null;
    private static PostPersistence postData = null;
    private static PagePersistence pageData = null;

    //this will retrieve the user stub, from which we can call functs such as getUser()
    public static synchronized UserPersistence getUserData() {
        //if the stub hasn't been created yet, create it
        if(userData == null) {
            userData = new UserPersistenceStub();
        }
        return userData;
    }

    public static synchronized PostPersistence getPostData() {
        if(postData == null) {
            postData = new PostPersistenceStub();
        }
        return postData;
    }

    public static synchronized PagePersistence getPageData() {
        if(pageData == null) {
            pageData = new PagePersistenceStub();
        }
        return pageData;
    }
//    public static void addfakeUsers(){
//
//
//
//        User user1=new User("newUser","jay","234569718"),user2=new User("hiUser","aaron","12345266"),user3=new User("helloUser","lucas","1234567");
//        getUserData().insertNewUser(user1);
//        getUserData().insertNewUser(user2);
//        getUserData().insertNewUser(user3);
//
//        int[] imageId= {R.drawable.gradient1,R.drawable.gradient1,R.drawable.gradient1,R.drawable.gradient1,R.drawable.gradient1};
//        getPostData().insertNewPost(new PicturePost("Something!I don't know",user1,imageId[0],123,123,123,user1.getPersonalPage()));
//        getPostData().insertNewPost(new PicturePost("Something!I don't know",user2,imageId[1],123,123,123,user2.getPersonalPage()));
//        getPostData().insertNewPost(new PicturePost("Something!I don't know",user1,imageId[3],123,123,123,user1.getPersonalPage()));
//
//    }
}
