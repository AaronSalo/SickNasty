package com.sicknasty.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sicknasty.business.AccessUsers;
import com.sicknasty.objects.User;

import com.sicknasty.R;

public class RegistrationActivity extends AppCompatActivity {

    AccessUsers users=new AccessUsers();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //get the sign up details from the ui

        Button register=findViewById(R.id.Register);
        ((EditText) findViewById(R.id.signUpUsername)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(users.validNewUsername(s.toString())) {
                    Log.d("checking username", "you can use this username");

                }
            }
        });

        //validate the new account and create it
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = ((EditText)findViewById(R.id.signUpName)).getText().toString();
                String username = ((EditText)findViewById(R.id.signUpUsername)).getText().toString();
                String password = ((EditText)findViewById(R.id.signUpPassword)).getText().toString();

                if(signUpValidation(name, username, password)){
                    if(users.validNewUsername(username)){
                        users.insertUser(username,password);        //after validating the user
                        Toast toast = Toast.makeText(RegistrationActivity.this,"Sign Up successful :Now just Login",Toast.LENGTH_SHORT);
                        toast.show();
                        Intent startIntent=new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(startIntent);
                    }
                    else{
                        Toast toast = Toast.makeText(RegistrationActivity.this,"Try a different username",Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            }
        });
        Button signIn=findViewById(R.id.signIn);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(startIntent);
            }
        });

    }

    private boolean signUpValidation(String name,String username,String password){
        boolean res=false;

        if(name.isEmpty() && username.isEmpty() && password.isEmpty()){
            Toast.makeText(RegistrationActivity.this,"You cannot leave blanks empty",Toast.LENGTH_SHORT).show();
        }
        else if(name.isEmpty()){
            Toast toast = Toast.makeText(RegistrationActivity.this,"Enter your Name:",Toast.LENGTH_SHORT);
            toast.show();
        }
        else if(username.isEmpty()){
            Toast toast = Toast.makeText(RegistrationActivity.this,"Enter your Username",Toast.LENGTH_SHORT);
            toast.show();
        }
        else if(password.isEmpty()){
            Toast toast = Toast.makeText(RegistrationActivity.this,"Enter your password",Toast.LENGTH_SHORT);
            toast.show();
        }
        else
            res=true;
        return res;
    }
}
