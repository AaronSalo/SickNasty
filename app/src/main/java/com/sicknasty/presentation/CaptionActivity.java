package com.sicknasty.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.sicknasty.R;
import com.sicknasty.business.AccessPages;
import com.sicknasty.business.AccessPosts;
import com.sicknasty.business.AccessUsers;
import com.sicknasty.objects.Exceptions.CaptionTextException;
import com.sicknasty.objects.Exceptions.NoValidPageException;
import com.sicknasty.objects.Page;
import com.sicknasty.objects.Post;
import com.sicknasty.objects.User;
import com.sicknasty.persistence.exceptions.DBPageNameNotFoundException;
import com.sicknasty.persistence.exceptions.DBPostIDExistsException;
import com.sicknasty.persistence.exceptions.DBUsernameNotFoundException;

public class CaptionActivity extends AppCompatActivity {

    AccessPosts posts = new AccessPosts();
    AccessUsers users = new AccessUsers();
    AccessPages pages = new AccessPages();
    String pageName = null;
    User curUser = null;

    Page currPage =  null;

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caption);

        final EditText caption = findViewById(R.id.captionText);
        Button postButton = findViewById(R.id.captionPost);
        ImageView imageView = findViewById(R.id.postImage);

        Intent intent = getIntent();                              //get the intent from last activity

        final String uri = intent.getStringExtra("URI");          //this is what is displaying selected image when choosing
        Uri uri1 = Uri.parse(uri);
        imageView.setImageURI(uri1);                                    //display selected image


        preferences = getSharedPreferences("MY_PREFS",MODE_PRIVATE);
        pageName = intent.getStringExtra("pageName");
        try {
            curUser = users.getUser(preferences.getString("username",null));
            currPage = pages.getPage(pageName);
        } catch (DBUsernameNotFoundException | DBPageNameNotFoundException e) {
            String errorMsg = e.getMessage();
            Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_SHORT).show();
        }

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String captionText = caption.getText().toString();

                try {
                    Post newPost = new Post(captionText, curUser, uri, 0, 0, currPage);
                    posts.insertPost(newPost);          //only insert after adding a caption(move to captionActivity)
                    goToHome();
                } catch (DBPostIDExistsException | NoValidPageException | CaptionTextException e) {
                    Toast.makeText(CaptionActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void goToHome(){
        Intent backToPageIntent;
        if(checkPageName(pageName)){
            backToPageIntent = new Intent(CaptionActivity.this, LoggedUserPageActivity.class);
            backToPageIntent.putExtra("user",pageName);
        }
        else{
            backToPageIntent = new Intent(CaptionActivity.this, CommunityPageActivity.class);
            backToPageIntent.putExtra("currentCommunityPage",pageName);
        }

        startActivity(backToPageIntent);
        finish();
    }

    private boolean checkPageName(String pageName){
        return curUser.getUsername().equals(pageName);
    }
}
