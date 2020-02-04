package com.sicknasty.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.sicknasty.objects.User;

import com.sicknasty.R;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //get the sign up details from the ui
        String name = findViewById(R.id.signUpName).toString();
        String username = findViewById(R.id.signUpUsername).toString();
        String password = findViewById(R.id.signUpPassword).toString();

        //validate the new account and create it
        User newUser = signUpValidation(name, username, password);
    }

    private User signUpValidation(String name, String username, String password) {
        if(name != null) {
            
        }
        return null;
    }
}
