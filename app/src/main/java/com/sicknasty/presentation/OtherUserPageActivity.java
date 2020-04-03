package com.sicknasty.presentation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
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
import com.sicknasty.persistence.exceptions.DBUserAlreadyFollowingException;
import com.sicknasty.persistence.exceptions.DBUsernameNotFoundException;
import com.sicknasty.presentation.adapter.PostAdapter;

public class OtherUserPageActivity extends AppCompatActivity {

    AccessUsers users = new AccessUsers();
    AccessPages pages = new AccessPages();
    AccessPosts posts = new AccessPosts();
    SharedPreferences sharedPreferences;
    Page page;
    User loggedUser ,thisUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_page);

        TextView followers = findViewById(R.id.Otherfollowers);
        TextView following = findViewById(R.id.Otherfollowing);
        TextView numberOfPosts = findViewById(R.id.Otherposts);

        final Button followButton = findViewById(R.id.followButton);
        Button messageButton = findViewById(R.id.messageButton);
        ListView listView = findViewById(R.id.lvOtherPost);
        Button homeButton = findViewById(R.id.home);
        TextView name = findViewById(R.id.otherProfileName);

        Intent intent = getIntent();
        final String userName = intent.getStringExtra("user");
        String message;

        sharedPreferences = getSharedPreferences("MY_PREFS", MODE_PRIVATE);
        final String loggedInUsername = sharedPreferences.getString("username",null);       //this is necessary for message
        int numOfPosts = 0;
        PostAdapter postAdapter = null;
        try {
            loggedUser = users.getUser(loggedInUsername);
            thisUser = users.getUser(userName);             //thisUser is the user whose page we are looking at
            page = pages.getPage(userName);        //again ,remember pageName is Username
            postAdapter = new PostAdapter(this, R.layout.activity_post, posts.getPostsByPage(page));
            numOfPosts = posts.getPostsByPage(page).size();
        } catch (DBPageNameNotFoundException | NoValidPageException | UserNotFoundException | DBUsernameNotFoundException e) {
            message = e.getMessage();
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
        name.setText(thisUser.getName());
        followers.setText(""+(int)(100*Math.random()));
        numberOfPosts.setText(""+numOfPosts);
        following.setText(""+(int)(100*Math.random()));

        listView.setAdapter(postAdapter);


        //also set all the things we did in userPage(followers ,following and posts)
        followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    pages.addFollower(page,loggedUser);
                    followButton.setText("Following");

                } catch (DBUserAlreadyFollowingException e) {
                    Toast.makeText(OtherUserPageActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent=new Intent(OtherUserPageActivity.this, LoggedUserPageActivity.class);
                homeIntent.putExtra("user", loggedInUsername);
                startActivity(homeIntent);
                finish();
            }
        });

        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent messageIntent = new Intent(OtherUserPageActivity.this,MessageActivity.class);
                messageIntent.putExtra("loggedInUser", loggedInUsername);       //sends whos logged in to message activity
                messageIntent.putExtra("currentUser", userName);            //sends the person being sent the message to message activity
                startActivity(messageIntent);
                finish();
            }
        });
    }
}
