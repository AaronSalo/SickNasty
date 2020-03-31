package com.sicknasty.presentation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.sicknasty.R;
import com.sicknasty.application.Service;
import com.sicknasty.business.AccessUsers;
import com.sicknasty.objects.Exceptions.UserNotFoundException;
import com.sicknasty.objects.User;
import com.sicknasty.persistence.exceptions.DBUsernameNotFoundException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class LoginActivity extends AppCompatActivity {
    AccessUsers userHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // the order here is VERY important, this should be one of the first things that get executed
        this.copyDBToDevice();
        this.userHandler = new AccessUsers();

        SharedPreferences saveLoginDetails=getSharedPreferences("MY_PREFS",MODE_PRIVATE);           //saving user details so that
        final SharedPreferences.Editor prefEditor=saveLoginDetails.edit();                                //they don't have to login everytime they open app

        final EditText userName = findViewById(R.id.userName);
        final EditText password = findViewById(R.id.password);
        Button login = findViewById(R.id.Login);
        Button register=findViewById(R.id.signUp);

        //page already exists (toast from where????)
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

                if(validateInput(inputUsername, inputPassword)){ //check sure we have a valid input

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
                    } catch (UserNotFoundException | DBUsernameNotFoundException e) {
                        infoText = e.getMessage();
                    } finally {
                        //show the user the appropriate message
                        Toast.makeText(getApplicationContext(),infoText,Toast.LENGTH_SHORT).show();
                    }
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


    //make sure the user actually input some values
    private boolean validateInput(String username,String password){
        username = username.trim();
        password = password.trim();

        String infoText = "";

        boolean result=false;
        if(username.isEmpty() && password.isEmpty()){
            infoText = "enter your username and password";
        }
        else if(username.isEmpty()){
            infoText = "Enter your username";
        }
        else if(password.isEmpty()){
            infoText = "Enter your password";
        }
        else
            result = true;

        //if we have a message for the user, display it
        if(infoText.length() > 0) {
            Toast toast = Toast.makeText(LoginActivity.this, infoText, Toast.LENGTH_SHORT);
            toast.show();
        }
        return result;
    }

    private void copyDBToDevice() {
        // setting up constants
        final String TAG = "LOGIN";
        final String DB_NAME = "sicknasty";

        Log.d(TAG, "Getting hidden system folder");

        // get app context to get its private sys folder
        Context appContext = getApplicationContext();

        // find the private sys folder
        File dir = appContext.getDir("db", Context.MODE_PRIVATE);
        // create a new script file
        File dbFile = new File(dir.toString() + "/" + DB_NAME + ".script");

        Log.d(TAG, "Saving pathname");
        Service.setPathName(dbFile.toString());

        // only copy contents if we dont have existing file
        if (!dbFile.exists()) {
            Log.d(TAG, "Setting up new database");

            AssetManager assets = getAssets();

            try {
                // open streams for file inputs
                InputStream appFile = assets.open(DB_NAME + ".script");
                InputStreamReader inputStream = new InputStreamReader(appFile);

                // open stream for output
                FileWriter outputStream = new FileWriter(dbFile);

                // read in 1kb data
                char buffer[] = new char[1024];
                int amnt = inputStream.read(buffer);

                // write until EOF
                while (amnt != -1) {
                    Log.d(TAG, "Writing...");
                    outputStream.write(buffer, 0, amnt);

                    amnt = inputStream.read(buffer);
                }

                inputStream.close();
                outputStream.close();

                Log.d(TAG, "Finished copying");
            } catch (IOException e) {
                Log.e(TAG, "Unable to copy database to device: " + e);
            }
        }
    }
}
