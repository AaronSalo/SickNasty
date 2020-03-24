package com.sicknasty.presentation;

import android.content.Intent;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.sicknasty.R;
import com.sicknasty.business.AccessPages;
import com.sicknasty.business.AccessPosts;
import com.sicknasty.business.AccessUsers;
import com.sicknasty.objects.Exceptions.NoValidPageException;
import com.sicknasty.objects.Exceptions.UserNotFoundException;
import com.sicknasty.objects.Page;
import com.sicknasty.objects.User;
import com.sicknasty.persistence.exceptions.DBPageNameNotFoundException;
import com.sicknasty.persistence.exceptions.DBUsernameNotFoundException;
import com.sicknasty.presentation.adapter.PostAdapter;

public class OtherUserPageActivity extends AppCompatActivity {

    AccessUsers users = new AccessUsers();
    AccessPages pages = new AccessPages();
    AccessPosts posts = new AccessPosts();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_page);

        Button followButton = findViewById(R.id.followButton);
        Button messageButton = findViewById(R.id.messageButton);
        ListView listView = findViewById(R.id.lvOtherPost);
        Button homeButton = findViewById(R.id.home);


        Intent intent = getIntent();
        final String userName = intent.getStringExtra("user");
        String message;
        final String loggedInUser = getSharedPreferences("MY_PREFS",MODE_PRIVATE).getString("username",null);       //this is necessary for message

        PostAdapter postAdapter = null;
        try {
            Page page = pages.getPage(userName);        //again ,remember pageName is Username
            postAdapter = new PostAdapter(this, R.layout.activity_post, posts.getPostsByPage(page));
        } catch (DBPageNameNotFoundException | NoValidPageException e) {
            message = e.getMessage();
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }


        listView.setAdapter(postAdapter);


        //also set all the things we did in userPage(followers ,following and posts)
        try {
            User this_user = users.getUser(userName);
        } catch (UserNotFoundException | DBUsernameNotFoundException e) {
            message =  e.getMessage();
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }

        followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });





        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    try {
                        Intent startIntent=new Intent(OtherUserPageActivity.this, LoggedUserPageActivity.class);
                        startIntent.putExtra("user", loggedInUser);
                        startActivity(startIntent);
                        finish();

                    } catch (Exception e) {
                        //do something here
                    }
            }
        });


        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(OtherUserPageActivity.this,MessageActivity.class);
                newIntent.putExtra("loggedInUser", loggedInUser);       //sends whos logged in to message activity
                newIntent.putExtra("currentUser", userName);            //sends the person being sent the message to message activity
                startActivity(newIntent);
                finish();
            }
        });
    }
}
