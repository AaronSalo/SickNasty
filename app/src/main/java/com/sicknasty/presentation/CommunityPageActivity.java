package com.sicknasty.presentation;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.sicknasty.R;
import com.sicknasty.business.AccessPages;
import com.sicknasty.business.AccessPosts;
import com.sicknasty.business.AccessUsers;
import com.sicknasty.objects.Exceptions.UserNotFoundException;
import com.sicknasty.objects.User;
import com.sicknasty.persistence.exceptions.DBUsernameNotFoundException;


public class CommunityPageActivity extends AppCompatActivity {
    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;

    public String currCommunityName =  null;
    public User curUser;

    AccessUsers users = new AccessUsers();
    AccessPages pages=new AccessPages();
    AccessPosts posts = new AccessPosts();
    String curUserName=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);

        ListView lvPostsCommunity = findViewById(R.id.lvPostsCommunity);
        TextView numberOfFollower =findViewById(R.id.followerComm);
        TextView numberOfPosts= findViewById(R.id.postsCount);
        Button postButton=findViewById(R.id.postCommunity);

        final String loggedInUser = getSharedPreferences("MY_PREFS", MODE_PRIVATE).getString("username", null);
        curUserName = loggedInUser;

        getData();

        numberOfFollower.setText("" + 1000);
        numberOfPosts.setText("" + 1000);


        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isLoggedUser(loggedInUser)){
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
                else{
                    Toast.makeText(CommunityPageActivity.this,"You cannot post to other account", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void chooseImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), IMAGE_PICK_CODE);
    }


    private boolean isLoggedUser(String loggedInUser){
        Log.d("AAAAAA",loggedInUser.equals(getIntent().getStringExtra("user"))+"");
        return loggedInUser.equals(getIntent().getStringExtra("user"));
    }

    private void getData() {
        Intent intent=getIntent();
        try {

            if(isLoggedUser(curUserName)) {
                Log.e("hello", curUserName);
                curUser = users.getUser(curUserName);
            }else{
                curUser=users.getUser(intent.getStringExtra("user"));
            }
            currCommunityName = curUser.getUsername();
            ((TextView) findViewById(R.id.profileName)).setText(curUser.getName());

        } catch (UserNotFoundException | DBUsernameNotFoundException e) {
            String errorMsg = e.getMessage();
            Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_SHORT).show();
        }
    }
}
