package com.sicknasty.presentation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.sicknasty.R;
import com.sicknasty.application.Service;
import com.sicknasty.business.AccessUsers;
import com.sicknasty.objects.User;
import com.sicknasty.persistence.exceptions.DBUsernameNotFoundException;

import java.io.File;


public class LoginActivity extends AppCompatActivity {
    AccessUsers userHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // create database file
        // the reason this is here is because getApplicationContext() is only located here
        //
        // so in the sample project, they do a similar thing in the UI/main activity
        // we need to create a script file for the database. that requires us to access the application context
        // which we access from here. the sample does a similar thing where they
        // create and copy over a file from the app to the device and call an set path function else where
        File dir = getApplicationContext().getDir("db", Context.MODE_PRIVATE);
        File dbFile = new File(dir.toString() + "/sicknasty.script");
        Service.setDBPathName(dbFile.toString());

        this.userHandler = new AccessUsers();

        SharedPreferences saveLoginDetails=getSharedPreferences("MY_PREFS",MODE_PRIVATE);           //saving user details so that
        final SharedPreferences.Editor prefEditor=saveLoginDetails.edit();                                //they don't have to login everytime they open app

        final EditText userName = findViewById(R.id.userName);
        final EditText password = findViewById(R.id.password);
        Button login = findViewById(R.id.Login);
        Button register=findViewById(R.id.signUp);

        if(saveLoginDetails.getBoolean("isLogin",false)){
            Intent startIntent = new Intent(LoginActivity.this, LoggedUserPageActivity.class);
            String loggedInUser = saveLoginDetails.getString("username",null);
            startIntent.putExtra("user", loggedInUser);
            startActivity(startIntent);
            finish();
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputUsername = userName.getText().toString();
                String inputPassword = password.getText().toString();


                if(!inputUsername.isEmpty() && !inputPassword.isEmpty()){ //check sure we have a valid input

                    //some text we are going to show the user
                    //its going to get changed, so if it doesn't, we have an unexpected error
                    String infoText = "An unexpected error has occurred";

                    try {
                        //get the user
                        User currUser = userHandler.getUser(inputUsername);
                        //check if the password is correct
                        if(currUser.checkPasswordCorrect(inputPassword) )
                        {
                            prefEditor.putBoolean("isLogin",true);
                            prefEditor.putString("username",inputUsername);
                            prefEditor.putString("password",inputPassword);
                            prefEditor.apply();
                            Intent startIntent = new Intent(LoginActivity.this, LoggedUserPageActivity.class);
                            startIntent.putExtra("user",  inputUsername);
                            startActivity(startIntent);
                            infoText = "Login Successful";
                            finish();
                        }
                        else{
                            infoText = "Password and username doesn't match";
                        }
                    } catch (DBUsernameNotFoundException e) {
                        infoText = e.getMessage();
                    } finally {
                        //show the user the appropriate message
                        Toast.makeText(getApplicationContext(),infoText,Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(),"Please enter all details",Toast.LENGTH_SHORT).show();
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent=new Intent(getApplicationContext(),RegistrationActivity.class);
                startActivity(startIntent);
            }
        });
    }
}
