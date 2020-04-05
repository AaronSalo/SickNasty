package com.sicknasty.presentation;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.sicknasty.R;
import com.sicknasty.business.AccessPages;
import com.sicknasty.business.AccessPosts;
import com.sicknasty.business.AccessUsers;
import com.sicknasty.objects.*;
import com.sicknasty.objects.Exceptions.NoValidPageException;
import com.sicknasty.persistence.exceptions.DBPageNameNotFoundException;
import com.sicknasty.persistence.exceptions.DBUsernameNotFoundException;
import com.sicknasty.presentation.adapter.PostAdapter;
import com.sicknasty.objects.Exceptions.UserNotFoundException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import android.widget.TextView;
import android.widget.Toast;

public class LoggedUserPageActivity extends AppCompatActivity {

    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;

    AccessUsers users = new AccessUsers();
    AccessPages pages = new AccessPages();
    AccessPosts posts = new AccessPosts();

    public User currUser;
    Boolean editProfilePic = false;         //this is what's differentiating between upload a post vs update profile pic
    public String pageName = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_page);

        ListView lvPost = findViewById(R.id.lvPost);         //listView of posts
        TextView followers = findViewById(R.id.followers);
        TextView following = findViewById(R.id.following);
        TextView numberOfPosts = findViewById(R.id.posts);
        Button postButton = findViewById(R.id.postButton);
        TextView name = findViewById(R.id.profileName);
        Button searchButton = findViewById(R.id.searchButton);
        ImageView profilePicEdit = findViewById(R.id.profilePicUpdate);
        ImageView settings = findViewById(R.id.settings);


        final String loggedInUser = getSharedPreferences("MY_PREFS",MODE_PRIVATE).getString("username",null);
        pageName = loggedInUser;
        PostAdapter postAdapter = null;
        int numOfPosts = 0;      //this indicates how many posts this page/user has
        try {
            currUser = users.getUser(loggedInUser);
            Page page = pages.getPage(loggedInUser);        //remember username is same as pageName
            postAdapter = new PostAdapter(this, R.layout.activity_post, posts.getPostsByPage(page));
            numOfPosts = posts.getPostsByPage(page).size();
        } catch (UserNotFoundException | DBUsernameNotFoundException | DBPageNameNotFoundException | NoValidPageException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        name.setText(currUser.getName());
        followers.setText(""+(int)(100*Math.random()));
        numberOfPosts.setText(""+numOfPosts);
        following.setText(""+(int)(100*Math.random()));
        lvPost.setAdapter(postAdapter);

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(LoggedUserPageActivity.this, UserAccountActivity.class);
                startActivity(newIntent);
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent=new Intent(LoggedUserPageActivity.this,SearchActivity.class);
                startActivity(newIntent);
            }
        });
        //also filter by post!! UI
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
                    Intent newIntent = new Intent(LoggedUserPageActivity.this, CaptionActivity.class);
                    newIntent.putExtra("pageName", pageName);
                    newIntent.putExtra("URI", uri.toString());
                    startActivity(newIntent);
                }
            }
        }
    }
}
