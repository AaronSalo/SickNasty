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
import com.sicknasty.objects.Chat;
import com.sicknasty.objects.Exceptions.UserNotFoundException;
import com.sicknasty.objects.User;

public class MessageActivity extends AppCompatActivity {

    AccessUsers userHandler;

    public Chat chat;


    private EditText editText;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_chat);

            editText = findViewById(R.id.editText);


        }

    public void sendMessage(View view) {
        String message = editText.getText().toString();
        if (message.length() > 0) {
            editText.getText().clear();
        }
    }
}


