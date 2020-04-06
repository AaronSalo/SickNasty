package com.sicknasty.presentation;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

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
        ImageView settings = findViewById(R.id.settings);
        TextView followers = findViewById(R.id.followers);
        TextView following = findViewById(R.id.following);
        TextView numberOfPosts = findViewById(R.id.posts);
        TextView name = findViewById(R.id.profileName);

        Button postButton = findViewById(R.id.postButton);
        Button searchButton = findViewById(R.id.searchButton);
        Button communityListButton = findViewById(R.id.communityListButton);

        SharedPreferences preferences = getSharedPreferences("MY_PREFS", MODE_PRIVATE);
        final String loggedInUser = preferences.getString("username", null);

        pageName = loggedInUser;
        PostAdapter postAdapter = null;
        int numOfPosts = 0;      //this indicates how many posts this page/user has

        try {
            currUser = users.getUser(loggedInUser);
            Page page = pages.getPage(loggedInUser);        //remember username is same as pageName
            ArrayList<Post> pagePosts = posts.getPostsByPage(page);

            postAdapter = new PostAdapter(this, R.layout.activity_post, pagePosts);
            numOfPosts = pagePosts.size();
        } catch (UserNotFoundException | DBUsernameNotFoundException | DBPageNameNotFoundException | NoValidPageException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        name.setText(currUser.getName());
        followers.setText("" + (int) (100 * Math.random()));
        numberOfPosts.setText("" + numOfPosts);
        following.setText("" + (int) (100 * Math.random()));

        lvPost.setAdapter(postAdapter);

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settingsIntent = new Intent(LoggedUserPageActivity.this, UserAccountActivity.class);
                startActivity(settingsIntent);
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchIntent = new Intent(LoggedUserPageActivity.this, SearchActivity.class);
                startActivity(searchIntent);
            }
        });

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImageHelper();
            }
        });

        communityListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showCommunityListIntent = new Intent(LoggedUserPageActivity.this, CommunityListPageActivity.class);
                startActivity(showCommunityListIntent);
            }
        });

    }

    private void chooseImageHelper() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int hasReadPermission = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);

            if (hasReadPermission == PackageManager.PERMISSION_DENIED){
                String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                requestPermissions(permissions, PERMISSION_CODE);
            } else{
                //access granted
                chooseImage();
            }
        } else {
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
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    chooseImage();
                } else{
                    Toast.makeText(this,"Permission denied!!", Toast.LENGTH_SHORT).show();
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
                Intent newPostIntent = new Intent(LoggedUserPageActivity.this, CaptionActivity.class);
                newPostIntent.putExtra("pageName", pageName);
                newPostIntent.putExtra("URI", uri.toString());
                startActivity(newPostIntent);
            }
        }
    }
}
