package com.sicknasty.presentation;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sicknasty.R;
import com.sicknasty.business.AccessPages;
import com.sicknasty.business.AccessPosts;
import com.sicknasty.objects.Exceptions.NoValidPageException;
import com.sicknasty.objects.Page;
import com.sicknasty.objects.Post;
import com.sicknasty.persistence.exceptions.DBPageNameNotFoundException;
import com.sicknasty.presentation.adapter.PostAdapter;

import java.util.ArrayList;


public class CommunityPageActivity extends AppCompatActivity {
    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;

    AccessPages pages;
    AccessPosts posts = new AccessPosts();
    private String pageName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);

        ListView lvPostsCommunity = findViewById(R.id.lvPostsCommunity);
        TextView numberOfPosts = findViewById(R.id.communityPostCount);
        TextView name = findViewById(R.id.communityName);
        Button postButton = findViewById(R.id.communityPostButton);

        pages = new AccessPages();
        posts = new AccessPosts();

        PostAdapter postAdapter = null;

        Intent intent = getIntent();
        pageName = intent.getStringExtra("currentCommunityPage");       //how to fetch from search activity

        name.setText(pageName);

        Page currPage;
        int postSize = 0;

        try {
            currPage = pages.getPage(pageName);
            ArrayList<Post> pagePosts = posts.getPostsByPage(currPage);

            postAdapter = new PostAdapter(this, R.layout.activity_post, pagePosts);
            postSize = pagePosts.size();
        } catch (DBPageNameNotFoundException | NoValidPageException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        lvPostsCommunity.setAdapter(postAdapter);
        numberOfPosts.setText("" + postSize);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImageHelper();
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
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    chooseImage();
                } else {
                    Toast.makeText(this, "Permission denied!!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_PICK_CODE) {
            if (resultCode == RESULT_OK && data != null && data.getData() != null) {       //if request is successful
                Uri uri = data.getData();

                Intent newIntent = new Intent(CommunityPageActivity.this, CaptionActivity.class);
                newIntent.putExtra("pageName", pageName);
                newIntent.putExtra("URI", uri.toString());
                startActivity(newIntent);
            }
        }
    }
}
