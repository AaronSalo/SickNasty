package com.sicknasty.presentation;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.sicknasty.R;
import com.sicknasty.business.AccessPages;
import com.sicknasty.business.AccessPosts;
import com.sicknasty.business.AccessUsers;
import com.sicknasty.objects.Exceptions.UserNotFoundException;
import com.sicknasty.objects.User;
import com.sicknasty.persistence.exceptions.DBUsernameNotFoundException;


public class CommunityPageActivity extends AppCompatActivity {
    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;

    public String currCommunityName =  null;
    public User curUser;

    AccessUsers users = new AccessUsers();
    AccessPages pages=new AccessPages();
    AccessPosts posts = new AccessPosts();
    String curUserName=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);

        ListView lvPostsCommunity = findViewById(R.id.lvPostsCommunity);
        TextView numberOfFollower = findViewById(R.id.followerComm);
        TextView numberOfPosts = findViewById(R.id.postsCount);
        TextView name = findViewById(R.id.communityName);
        Button postButton = findViewById(R.id.postCommunity);


        Intent intent = getIntent();
        String pageName = intent.getStringExtra("currentCommunityPage");

        name.setText(pageName);


    }
}
