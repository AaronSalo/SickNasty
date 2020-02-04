package com.sicknasty.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.sicknasty.R;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText userName=findViewById(R.id.userName);
        final EditText password=findViewById(R.id.password);
        Button login =findViewById(R.id.Login);
        Button register=findViewById(R.id.Register);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateUser(userName,password);
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
    public boolean validateUser(EditText userName,EditText password){
        if(userName.getText().toString().isEmpty()){


        }
        if(password.getText().toString().isEmpty()){


        }

        return false;
    }
}
