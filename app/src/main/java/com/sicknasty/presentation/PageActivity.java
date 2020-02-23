package com.sicknasty.presentation;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import com.sicknasty.R;
import com.sicknasty.business.AccessPages;
import com.sicknasty.business.AccessPosts;
import com.sicknasty.business.AccessUsers;
import com.sicknasty.objects.*;

import com.sicknasty.objects.Exceptions.UserNotFoundException;
import com.sicknasty.presentation.adapter.PostAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class PageActivity extends AppCompatActivity {

    private ListView lvPost;
    User currUser;
    AccessUsers users = new AccessUsers();
    AccessPages pages=new AccessPages();
    AccessPosts posts = new AccessPosts();
    private int PICK_IMAGE_REQUEST = 1;
    PostAdapter postAdapter;
    //private ImageView imageView=findViewById(R.id.imageView);
    public String pageName="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_page);
        lvPost = findViewById(R.id.lvPost);
        Button post=findViewById(R.id.postButton);
        getData();
        ((TextView)findViewById(R.id.followers)).setText("100");
        ((TextView)findViewById(R.id.following)).setText("100");


        //pass the updated list to the adapter
        ((TextView)findViewById(R.id.posts)).setText("0");

                                                                //remember to use separators(horizontal line)

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
                Log.d(" mcsc", "a.xkkdankamamAAAAAAAAAAAAAAAA");
                //startActivity(getIntent());
            }
        });


    }

    //Hard code here for post
    private void getData() {
        Intent intent = getIntent();
        try {
            User currUser = users.getUser(intent.getStringExtra("user"));
            pageName += intent.getStringExtra("user");
            ((TextView) findViewById(R.id.profileName)).setText("" + currUser.getName());
            pages.insertNewPage(currUser.getPersonalPage());

        } catch (UserNotFoundException e) {
            String errorMsg = e.getMessage();
            Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_SHORT).show();
            pages.insertNewPage(currUser.getPersonalPage());
        }
    }
    public void chooseImage() {
        Log.d("choose image","start choosing");
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        Log.d("chose image","end choosing");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.d("StativityForResult","AAAAAAAAAAAAAAAAAAAAAA");
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("AAAAAAAAAAAAAAAAAAAAAA","AAAAAAAAAAAAAAAAAAAAAA");
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
//            Log.d("AAAAAAAAAAAAAAAAAAAAAA","AAAAAAAAAAAAAAAAAAAAAA");
            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
//                Log.d("BBBBBBBBBBBBBBBBBBBBB","BBBBBBBBBABBBBBBBB");
                PicturePost post =new PicturePost("something",currUser,1,217713,0,0,currUser.getPersonalPage());
                post.setBm(bitmap);
                posts.insertPost(post);

                postAdapter= new PostAdapter(this,R.layout.activity_post,posts.getPostsByPage(pages.getPage(pageName)));
                lvPost.setAdapter(postAdapter);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
