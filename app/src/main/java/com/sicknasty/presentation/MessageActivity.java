package com.sicknasty.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.sicknasty.R;
import com.sicknasty.business.AccessUsers;
import com.sicknasty.objects.Exceptions.MessageException;
import com.sicknasty.objects.Message;
import com.sicknasty.objects.User;
import com.sicknasty.persistence.exceptions.DBUsernameNotFoundException;
import com.sicknasty.presentation.adapter.MessageAdapter;

public class MessageActivity extends AppCompatActivity {

    private AccessUsers users;
    private User curUser = null;
    private User loggedInUser = null;
    private MessageAdapter messageAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_chat);
            Intent intent=getIntent();
            Bundle extras;                  //users to get current user and logged in user from previous activity
            extras = intent.getExtras();

            users = new AccessUsers();
            try {                              //gets current user (person being messaged)
                curUser = users.getUser(extras.getString("currentUser"));
                loggedInUser = users.getUser(extras.getString("loggedInUser"));
            } catch (DBUsernameNotFoundException e) {
                String errorMsg = e.getMessage();
                Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_SHORT).show();
            }


            final ListView lvMessages =  findViewById(R.id.messages_view);      //lists messages
            TextView chatName = findViewById(R.id.chatname);             //chatName at the top of each chat
            ImageButton sendButton = findViewById(R.id.sendMessage);     //button that will send the message after entered
            final EditText message = findViewById(R.id.messageEntered);        //message to be sent to the listView
            chatName.setText(curUser.getName());                            //implements chatName


            //creates custom adapter so message text displays special way
            messageAdapter = new MessageAdapter(MessageActivity.this,users.getMessages(loggedInUser,curUser), loggedInUser, curUser);
            lvMessages.setAdapter(messageAdapter);                          //adapter to show messages in array list from database
            sendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String messageInput = message.getText().toString();

                    try{
                        Message message1 = new Message(messageInput,loggedInUser,curUser);
                        users.addMessage(message1);
                        messageAdapter = new MessageAdapter(MessageActivity.this, users.getMessages(loggedInUser,curUser),loggedInUser, curUser);

                        lvMessages.setAdapter(messageAdapter);             //adapter set here so list view updates right after a message is sent

                        messageAdapter.notifyDataSetChanged();

                        lvMessages.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);       //used to make the added message at the bottom of the list
                        lvMessages.setStackFromBottom(true);                                        // view and scrolls up when a new item is added

                        message.getText().clear();                      //clears message edittext when message is entered


                    } catch (MessageException e){
                        Toast.makeText(MessageActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    }
            });

            lvMessages.setAdapter(messageAdapter);             //this code needs to be repeated so all the messages show up and are scrolled to the very bottom.
            messageAdapter.notifyDataSetChanged();             //if not, then it is just a blank list view until a new message is input and then all the previous
                                                               //messages show up

            lvMessages.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);       //used to make the added message at the bottom of the list
            lvMessages.setStackFromBottom(true);                                        // view and scrolls up when a new item is added
    }

    @Override
    public void onBackPressed() {           //used to go back to previous activity when back button is pressed
        goToHome();
    }

    
    private void goToHome(){
        if(curUser!=null){
            Intent intent = new Intent(MessageActivity.this, OtherUserPageActivity.class);
            intent.putExtra("user",curUser.getUsername());
            startActivity(intent);
            finish();
        }
    }
}