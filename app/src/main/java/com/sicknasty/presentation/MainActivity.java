package com.sicknasty.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.sicknasty.R;
import com.sicknasty.business.AccessUsers;
import com.sicknasty.objects.User;


public class MainActivity extends AppCompatActivity {

    AccessUsers users =new AccessUsers();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText userName=findViewById(R.id.userName);
        final EditText password=findViewById(R.id.password);
        Button login =findViewById(R.id.Login);
        Button register=findViewById(R.id.signUp);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateUser(userName.getText().toString(),password.getText().toString())) {

                    User currUser=users.validNewUsername(userName.getText().toString());
                    Log.d("checkingUserdetails","dmavddvvdmd");
                    if(currUser!=null)                      //if successfull login add a toast
                    {
                        if(currUser.checkPasswordCorrect(password.getText().toString()))
                        {
                            Intent startIntent=new Intent(MainActivity.this,PageActivity.class);

                            startIntent.putExtra("user",  userName.getText().toString());
                            startActivity(startIntent);
                            Toast toast = Toast.makeText(getApplicationContext(),"Login Successful!",Toast.LENGTH_SHORT);
                            toast.show();

                            finish();
                        }
                        else{
                            Toast toast = Toast.makeText(getApplicationContext(),"Invalid Details",Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                }
                else {
                    Toast toast = Toast.makeText(getApplicationContext(),"Invalid Details",Toast.LENGTH_SHORT);
                    toast.show();
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
    private boolean validateUser(String username,String password){
        boolean res=false;
        if(username.isEmpty() && password.isEmpty()){
            Toast toast = Toast.makeText(MainActivity.this,"Enter your username and password",Toast.LENGTH_SHORT);
            toast.show();
        }
        else if(username.isEmpty()){
            Toast toast = Toast.makeText(MainActivity.this,"Enter your Username",Toast.LENGTH_SHORT);
            toast.show();
        }
        else if(password.isEmpty()){
            Toast toast = Toast.makeText(MainActivity.this,"Enter your password",Toast.LENGTH_SHORT);
            toast.show();
        }
        else
            res=true;
        return res;
    }
}
