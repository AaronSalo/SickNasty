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
    User loggedUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_page);

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

        PostAdapter postAdapter = null;
        try {
            loggedUser = users.getUser(loggedInUsername);
            page = pages.getPage(userName);        //again ,remember pageName is Username
            postAdapter = new PostAdapter(this, R.layout.activity_post, posts.getPostsByPage(page));
        } catch (DBPageNameNotFoundException | NoValidPageException | UserNotFoundException | DBUsernameNotFoundException e) {
            message = e.getMessage();
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
//        name.setText(curUser.getName());
//        followers.setText(""+(int)(100*Math.random()));
//        numberOfPosts.setText(""+postSize);
//        following.setText(""+(int)(100*Math.random()));

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
                Intent startIntent=new Intent(OtherUserPageActivity.this, LoggedUserPageActivity.class);
                startIntent.putExtra("user", loggedInUsername);
                startActivity(startIntent);
                finish();
            }
        });

        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(OtherUserPageActivity.this,MessageActivity.class);
                newIntent.putExtra("loggedInUser", loggedInUsername);       //sends whos logged in to message activity
                newIntent.putExtra("currentUser", userName);            //sends the person being sent the message to message activity
                startActivity(newIntent);
                finish();
            }
        });
    }
}
