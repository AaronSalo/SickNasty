package com.sicknasty.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
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
import com.sicknasty.objects.Exceptions.MessageException;
import com.sicknasty.objects.Exceptions.UserNotFoundException;
import com.sicknasty.objects.Message;
import com.sicknasty.objects.User;
import com.sicknasty.persistence.exceptions.DBUsernameNotFoundException;

public class MessageActivity extends AppCompatActivity {


    AccessUsers users=new AccessUsers();

    String updated=null;
    User curUser=null;
    User sendingtoUser=null;
    ArrayAdapter<Message> adapter;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_chat);


            Intent intent=getIntent();

            ListView lvMessages = findViewById(R.id.messages_view);      //lists messages
            TextView chatName = findViewById(R.id.chatname);             //chatname at the top of each chat
            ImageButton sendButton = findViewById(R.id.sendMessage);     //button that will send the message after entered
            final EditText message = findViewById(R.id.messageEntered);        //message to be sent to the listview

            adapter = new ArrayAdapter<>(MessageActivity.this,android.R.layout.simple_list_item_1,users.getMessages(curUser, sendingtoUser));




            try {
                curUser = users.getUser(intent.getStringExtra("pageName"));
            } catch (UserNotFoundException | DBUsernameNotFoundException e) {
                String errorMsg = e.getMessage();
                Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_SHORT).show();
            }

            final User finalCurUser = curUser;
            sendingtoUser = curUser;
            sendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String messageinput = message.getText().toString();
                    if(validateMsg(messageinput)) {

                        updated="accessed";

                        try{
                            Message message1 = new Message(messageinput,finalCurUser,sendingtoUser);
                            users.addMessage(message1);

                        }catch (MessageException e){

                            //add exception message

                        }


                    }

                }
            });

            lvMessages.setAdapter(adapter);             //adapter to show messages in array list from database

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

    @Override
    public void onBackPressed() {
        goToHome();
    }

    private void goToHome(){
        if(curUser!=null){
            Intent intent=new Intent(MessageActivity.this,PageActivity.class);
            intent.putExtra("user",curUser.getUsername());
            startActivity(intent);
            finish();
        }
    }




//    public void sendMessage(View view) {                    //should create message and add it to list view. when arrow is clicked
//        String message = editText.getText().toString();
//        if (message.length() > 0) {
//            editText.getText().clear();
//        }
//    }
}