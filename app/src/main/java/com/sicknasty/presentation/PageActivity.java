package com.sicknasty.presentation;

import android.content.Intent;
import android.os.Bundle;

import com.sicknasty.R;
import com.sicknasty.application.Service;
import com.sicknasty.business.AccessPages;
import com.sicknasty.business.AccessPosts;
import com.sicknasty.business.AccessUsers;
import com.sicknasty.objects.*;
import com.sicknasty.adapter.PostAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class PageActivity extends AppCompatActivity {

    private ListView lvPost;
    AccessUsers users = new AccessUsers();
    AccessPages pages=new AccessPages();
    AccessPosts posts = new AccessPosts();
    public String pageName="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_page);
        lvPost = findViewById(R.id.lvPost);

        getData();

        ((TextView)findViewById(R.id.followers)).setText("100");
        ((TextView)findViewById(R.id.following)).setText("100");
        ((TextView)findViewById(R.id.posts)).setText("10");

        PostAdapter postAdapter = new PostAdapter(this,R.layout.activity_post,posts.getPostsByPage(pages.getPage(pageName)));
        lvPost.setAdapter(postAdapter);

    }

    //Hard code here for post
    private void getData() {
        Intent intent =getIntent();
        User currUser=users.validNewUsername(intent.getStringExtra("user"));
        pageName+=intent.getStringExtra("user");
        ((TextView)findViewById(R.id.profileName)).setText(""+currUser.getName());
        int[] imageIds = {R.drawable.post1, R.drawable.post2,
                R.drawable.post3, R.drawable.post4,
                R.drawable.post5, R.drawable.post1,
                R.drawable.post2, R.drawable.post3,
                R.drawable.post4, R.drawable.post5};


        String[] text = {"Look at this sick dog trick!!!!!!!!!", "This post is sickkky sickkky nasty", "u should look at what i bought for 50$", "funny,right? no,you are so dumb", "screw life !! i want to suicide", "sarcasm at it's best","Look at this sick dog trick!!!!!!!!!","dont look at me ,i find you offensive dude","this is so cooll man","this post is dope man!!!!!"};

        int[] a={(int)(10*Math.random()),(int)(10*Math.random()),(int)(10*Math.random()),(int)(10*Math.random()),(int)(10*Math.random()),(int)(10*Math.random()),(int)(10*Math.random()),(int)(10*Math.random()),(int)(10*Math.random()),(int)(10*Math.random())};

        for (int i = 0; i < imageIds.length; i++) {
            PicturePost picturePost = new PicturePost(text[a[i]],currUser,imageIds[a[i]],123, 123, 123, currUser.getPersonalPage());
            posts.insertPost(picturePost);

        }
        pages.insertNewPage(currUser.getPersonalPage());
    }


}
