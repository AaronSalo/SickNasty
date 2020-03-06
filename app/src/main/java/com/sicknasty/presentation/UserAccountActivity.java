package com.sicknasty.presentation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sicknasty.R;
import com.sicknasty.business.AccessUsers;
import com.sicknasty.objects.Exceptions.ChangeUsernameException;
import com.sicknasty.objects.Exceptions.UserNotFoundException;
import com.sicknasty.persistence.exceptions.DBUsernameExistsException;
import com.sicknasty.persistence.exceptions.DBUsernameNotFoundException;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class UserAccountActivity extends AppCompatActivity {

    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;
    AccessUsers users=new AccessUsers();
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);

        Button submitPass=findViewById(R.id.submitPassword);
        Button submitUser=findViewById(R.id.submitUsername);

        final EditText newUserName=findViewById(R.id.newUserNameText);
        final EditText newPass=findViewById(R.id.newPasswordText);

        TextView profilePicture=findViewById(R.id.changeProfilePicture);

        Button logout=findViewById(R.id.logout);

        profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(UserAccountActivity.this, "Coming Iteration 3", Toast.LENGTH_SHORT).show();

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
        submitPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferences=getSharedPreferences("MY_PREFS",MODE_PRIVATE);
                String pass=newPass.getText().toString();
                if(pass.isEmpty()){
                    Toast.makeText(UserAccountActivity.this, "Enter a new Password", Toast.LENGTH_SHORT).show();
                }
                else{
                    //call similar functions
                    if(pass.length()<6)         //i did this bcz i didn't know how fn behaves
                        Toast.makeText(UserAccountActivity.this, "Enter a Password minimum 6 characters", Toast.LENGTH_SHORT).show();
                    else{

                        try {
                            users.updateUserPassword(preferences.getString("username",null),pass);
                            Toast.makeText(UserAccountActivity.this, "Password successfully updated", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(UserAccountActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
        submitUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName=newUserName.getText().toString();
                if(userName.isEmpty()){
                    Toast.makeText(UserAccountActivity.this, "Enter a new Username", Toast.LENGTH_SHORT).show();
                }
                else{
                    //call similar functions as in loginACtivity
                    preferences=getSharedPreferences("MY_PREFS",MODE_PRIVATE);
                    //update username in sharedPrefernces
                    try {
                        users.updateUsername(users.getUser(preferences.getString("username",null)),userName);
                        SharedPreferences.Editor editor=preferences.edit();
                        editor.remove("username");
                        editor.putString("username",userName);
                        editor.apply();
                        Toast.makeText(UserAccountActivity.this, "Username successfully updated", Toast.LENGTH_SHORT).show();
                    } catch (DBUsernameExistsException | ChangeUsernameException | UserNotFoundException | DBUsernameNotFoundException e) {
                        Toast.makeText(UserAccountActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                preferences=getSharedPreferences("MY_PREFS",MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences.edit();
                editor.remove("username");
                editor.remove("password");
                editor.remove("isLogin");
                editor.apply();

                Intent newIntent=new Intent(UserAccountActivity.this,LoginActivity.class);
                startActivity(newIntent);
                finish();
            }
        });
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
            //put the uri in page
        }
    }

    @Override
    public void onBackPressed() {
        goToHome();
    }

    public void goToHome(){
        Intent intent=new Intent(UserAccountActivity.this,PageActivity.class);
        preferences=getSharedPreferences("MY_PREFS",MODE_PRIVATE);
        intent.putExtra("user",preferences.getString("username",null));
        startActivity(intent);
        finish();
    }
}