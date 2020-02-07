package com.sicknasty.presentation;

import android.content.Intent;
import android.os.Bundle;

import com.sicknasty.R;
import com.sicknasty.application.Service;
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
    private List<PicturePost> postList = new ArrayList<PicturePost>();
    AccessUsers users = new AccessUsers();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_page);
        lvPost = findViewById(R.id.lvPost);


        getData();

        ((TextView)findViewById(R.id.followers)).setText("100");
        ((TextView)findViewById(R.id.following)).setText("100");
        ((TextView)findViewById(R.id.posts)).setText("100");

        PostAdapter postAdapter = new PostAdapter(this,R.layout.activity_post, postList);
        lvPost.setAdapter(postAdapter);

    }

    //Hard code here for post
    private void getData() {
        Intent intent =getIntent();
        User currUser=users.validNewUsername(intent.getStringExtra("user"));

        ((TextView)findViewById(R.id.profileName)).setText(""+currUser.getName());
        int[] imageIds = {R.drawable.logo, R.drawable.logo,
                R.drawable.logo, R.drawable.logo,
                R.drawable.logo, R.drawable.logo,
                R.drawable.logo, R.drawable.logo,
                R.drawable.logo, R.drawable.logo};
        String[] userName = {"a", "b", "c", "d", "e", "f", "g", "h", "i",
                "j"};
        String[] text = {",m dam", "b", "c", "d", "e", "f", "g", "h", "i",
                "j"};
        for (int i = 0; i < imageIds.length; i++) {
            postList.add(new PicturePost(text[i],currUser,imageIds[i],123, 123, 123, currUser.getPersonalPage()));
        }
    }


}
