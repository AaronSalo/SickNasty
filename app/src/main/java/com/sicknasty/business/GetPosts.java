package com.sicknasty.business;

import com.sicknasty.application.Service;
import com.sicknasty.objects.Post;
import com.sicknasty.persistence.stubs.PostPersistenceStub;

import java.util.ArrayList;

public class GetPosts {

    private PostPersistenceStub postHandler;
    private ArrayList<Post> posts;

    public GetPosts() {
        postHandler = Service.getPostData();
    }

    

}
