package com.sicknasty.presentation;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sicknasty.R;
import com.sicknasty.business.AccessPages;
import com.sicknasty.business.AccessPosts;
import com.sicknasty.business.AccessUsers;
import com.sicknasty.objects.CommunityPage;
import com.sicknasty.objects.Exceptions.NoValidPageException;
import com.sicknasty.objects.Exceptions.UserNotFoundException;
import com.sicknasty.objects.Page;
import com.sicknasty.objects.User;
import com.sicknasty.persistence.exceptions.DBPageNameNotFoundException;
import com.sicknasty.persistence.exceptions.DBUsernameNotFoundException;
import com.sicknasty.presentation.adapter.PostAdapter;


public class CommunityPageActivity extends AppCompatActivity {
    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;

    private User currUser;
    private Page currPage;
    private Boolean editProfilePic = false;

    AccessUsers users = new AccessUsers();
    AccessPages pages=new AccessPages();
    AccessPosts posts = new AccessPosts();
    private SharedPreferences preferences;
    private String pageName ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);
        PostAdapter postAdapter = null;

        ListView lvPostsCommunity = findViewById(R.id.lvPostsCommunity);
        TextView numberOfFollower = findViewById(R.id.followerCommCount);
        TextView numberOfPosts = findViewById(R.id.postsCount);
        TextView name = findViewById(R.id.communityName);
        Button postButton = findViewById(R.id.postCommunity);
        ImageView profilePicEdit = findViewById(R.id.profilePicUpdateCommunity);

        Intent intent = getIntent();
        pageName = intent.getStringExtra("currentCommunityPage");

        name.setText(pageName);

        preferences = getSharedPreferences("MY_PREFS", MODE_PRIVATE);

        final String loggerInUser =  preferences.getString("username",null);

        try {
            currUser = users.getUser(loggerInUser);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        } catch (DBUsernameNotFoundException e) {
            e.printStackTrace();
        }

        try {
            currPage = pages.getPage(pageName);
            numberOfFollower.setText(currPage.getFollowers().size());
            numberOfPosts.setText(currPage.getPostList().size());
            postAdapter = new PostAdapter(this, R.layout.activity_post, posts.getPostsByPage(currPage));

        } catch (DBPageNameNotFoundException | NoValidPageException e) {
            e.printStackTrace();
        }

        lvPostsCommunity.setAdapter(postAdapter);

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImageHelper();
            }
        });

        profilePicEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProfilePic = true;
                chooseImageHelper();
            }
        });

    }



    private void chooseImageHelper()
    {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
                String[] permissions ={Manifest.permission.READ_EXTERNAL_STORAGE};
                requestPermissions(permissions,PERMISSION_CODE);
            }
            else{
                //access granted
                chooseImage();
            }
        }
        else {
            //ose is less than marshmallow
            chooseImage();
        }
    }
    private void chooseImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), IMAGE_PICK_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_CODE:{
                if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    chooseImage();
                }
                else{
                    Toast.makeText(this,"Permission denied!!",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_PICK_CODE) {
            if (resultCode == RESULT_OK && data != null && data.getData() != null) {       //if request is successful
                //we will save the uri.getPath to the db
                //create a picture post and insert it to database

                Uri uri = data.getData();

                if(editProfilePic){
                    //we know we have to update profile pic and not upload a post
                    //save the uri      -Reminder for lucas to add a uri field in page table

                }
                else {
                    Intent newIntent = new Intent(CommunityPageActivity.this, CaptionActivity.class);
                    newIntent.putExtra("pageName", pageName);
                    newIntent.putExtra("URI", uri.toString());
                    startActivity(newIntent);
                }
            }
        }
    }
}
