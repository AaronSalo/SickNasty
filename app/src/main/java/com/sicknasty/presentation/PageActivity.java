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
import com.sicknasty.persistence.exceptions.DBPageNameExistsException;
import com.sicknasty.persistence.exceptions.DBPageNameNotFoundException;
import com.sicknasty.persistence.exceptions.DBPostIDExistsException;
import com.sicknasty.persistence.exceptions.DBUsernameNotFoundException;
import com.sicknasty.presentation.adapter.PostAdapter;
import com.sicknasty.objects.Exceptions.UserNotFoundException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import android.widget.TextView;
import android.widget.Toast;

public class PageActivity extends AppCompatActivity {
    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;



    AccessUsers users = new AccessUsers();
    AccessPages pages=new AccessPages();
    AccessPosts posts = new AccessPosts();

    public User curUser;
    public String pageName="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_page);

        ListView lvPost=findViewById(R.id.lvPost);         //listView of posts
        TextView followers= findViewById(R.id.followers);
        TextView following= findViewById(R.id.following);
        TextView numberOfPosts= findViewById(R.id.posts);
        Button postButton=findViewById(R.id.postButton);

        //fetch followers,following... and display on the page
        getData();              //fetch the user data and display page accordingly


        PostAdapter postAdapter = null;
        try {
            postAdapter = new PostAdapter(this, R.layout.activity_post, posts.getPostsByPage(pages.getPage(pageName)));
        } catch (DBPageNameNotFoundException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        lvPost.setAdapter(postAdapter);

        //remember to use separators(horizontal line) ---- reminder for JAY

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //I (Jay) have to revisit this condition
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
                else{
                    //ose is less than marshmallow
                    chooseImage();
                }
            }
        });
    }

    private void getData() {
        Intent intent = getIntent();
        try {
            User currUser = users.getUser(intent.getStringExtra("user"));
            pageName += intent.getStringExtra("user");
            curUser=currUser;
            ((TextView) findViewById(R.id.profileName)).setText(currUser.getName());
            pages.insertNewPage(currUser.getPersonalPage());

        } catch (UserNotFoundException | DBUsernameNotFoundException | DBPageNameExistsException e) {
            String errorMsg = e.getMessage();
            Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_SHORT).show();
        }
    }
    public void chooseImage() {
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
        if (requestCode == IMAGE_PICK_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {       //if request is successful
                //we will save the uri.getPath to the db
                //create a picture post and insert it to database

            Uri uri=data.getData();
            Post newPost=new Post("Something i don't know",curUser,uri.toString(),0,0,curUser.getPersonalPage());

            try {
                posts.insertPost(newPost);
            } catch (DBPostIDExistsException e) {
                // if this gets tripped, you have done something wrong
                // -Lucas
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }
    }
}
