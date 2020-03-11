package com.sicknasty.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.sicknasty.R;
import com.sicknasty.business.AccessUsers;
import com.sicknasty.objects.Chat;
import com.sicknasty.objects.Exceptions.UserNotFoundException;
import com.sicknasty.objects.User;

public class MessageActivity extends AppCompatActivity {

    AccessUsers users;

    public Chat chat;


    private EditText message;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_chat);


           ListView lvMessages = findViewById(R.id.messages_view);      //lists messages
           TextView chatName = findViewById(R.id.chatname);             //chatname at the top of each chat
           ImageButton sendButton = findViewById(R.id.sendMessage);     //button that will send the message after entered


           //getData(); need to get hardcoded data (array list holding messages and data like user sending and profile pic)
            message = findViewById(R.id.messageEntered);            //need to store this back into arraylist and display


            sendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    //have to send message inside text into the list view after creating message object
                    // and also adding to arraylist of messages



                }
            });





        }



        private boolean validateMsg(String message){        //makes sure something is entered into message to be sent,
                                                            //outputs error text is error found (0 character length)

            boolean returnVal = true;
            String returnText = "";
            message = message.trim();

            if(message.isEmpty()){
                returnText = "Message cannot have 0 characters";
                returnVal = false;

            }

            if(returnText.length() > 0) {
                Toast toast = Toast.makeText(MessageActivity.this, returnText, Toast.LENGTH_SHORT);
                toast.show();
            }
            return returnVal;
        }





//    public void sendMessage(View view) {                    //should create message and add it to list view. when arrow is clicked
//        String message = editText.getText().toString();
//        if (message.length() > 0) {
//            editText.getText().clear();
//        }
//    }
}