package com.sicknasty.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.sicknasty.R;
import com.sicknasty.business.AccessUsers;
import com.sicknasty.objects.Exceptions.UserNotFoundException;
import com.sicknasty.objects.User;
import com.sicknasty.persistence.exceptions.DBUsernameNotFoundException;


public class LoginActivity extends AppCompatActivity {

    AccessUsers userHandler =new AccessUsers();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText userName=findViewById(R.id.userName);
        final EditText password=findViewById(R.id.password);
        Button login =findViewById(R.id.Login);
        Button register=findViewById(R.id.signUp);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputUsername = userName.getText().toString();
                String inputPassword = password.getText().toString();

                if(validateInput(inputUsername, inputPassword)){ //check sure we have a valid input

                    //some text we are going to show the user
                    //its going to get changed, so if it doesnt, we have an unexpected error
                    String infoText = "An unexpected error has occurred";

                    try {
                        //get the user
                        User currUser = userHandler.getUser(inputUsername);
                        //check if the password is correct
                        if(currUser.checkPasswordCorrect(inputPassword) )
                        {
                            Intent startIntent=new Intent(LoginActivity.this,PageActivity.class);
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
}
