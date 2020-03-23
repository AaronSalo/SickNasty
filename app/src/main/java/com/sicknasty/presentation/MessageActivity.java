package com.sicknasty.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.sicknasty.objects.Exceptions.NoValidPageException;
import com.sicknasty.objects.Exceptions.UserNotFoundException;
import com.sicknasty.objects.Message;
import com.sicknasty.objects.User;
import com.sicknasty.persistence.exceptions.DBPageNameNotFoundException;
import com.sicknasty.persistence.exceptions.DBUsernameNotFoundException;
import com.sicknasty.presentation.adapter.MessageAdapter;

public class MessageActivity extends AppCompatActivity {

    AccessUsers users=new AccessUsers();
    User curUser=null;
    User loggedInUser = null;
    MessageAdapter messageAdapter = null;


        @Override
        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_chat);
            Intent intent=getIntent();
            Bundle extras;                  //useds to get current user and logged in user from previous activity
            extras = intent.getExtras();



            try {                              //gets current user (person being messaged) and puts it into curUser var
                curUser = users.getUser(extras.getString("currentUser"));
                Log.d("currentUser",(curUser.getName()));
            } catch (UserNotFoundException | DBUsernameNotFoundException e) {
                String errorMsg = e.getMessage();
                Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_SHORT).show();
            }



            try {                               //gets Logged in user and puts it into curUser var
                loggedInUser = users.getUser(extras.getString("loggedInUser"));
                Log.d("loggedInUser",(loggedInUser.getName()));
            } catch (UserNotFoundException | DBUsernameNotFoundException e) {
                String errorMsg = e.getMessage();
                Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_SHORT).show();
            }




            final ListView lvMessages = (ListView) findViewById(R.id.messages_view);      //lists messages
            TextView chatName = findViewById(R.id.chatname);             //chatname at the top of each chat
            ImageButton sendButton = findViewById(R.id.sendMessage);     //button that will send the message after entered
            final EditText message = findViewById(R.id.messageEntered);        //message to be sent to the listview
            chatName.setText(curUser.getName());                            //implements chatname




            //creates custom adapter so message text displays speacial way
            messageAdapter = new MessageAdapter(MessageActivity.this,users.getMessages(loggedInUser,curUser), loggedInUser, curUser);
            lvMessages.setAdapter(messageAdapter);             //adapter to show messages in array list from database
            sendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String messageinput = message.getText().toString();

                    if(validateMsg(messageinput)) {

                        try{
                            Message message1 = new Message(messageinput,loggedInUser,curUser);
                            users.addMessage(message1);
                            messageAdapter = new MessageAdapter(MessageActivity.this, users.getMessages(loggedInUser,curUser),loggedInUser, curUser);

                            Log.d("messageAdapter problem", message.toString());

                            lvMessages.setAdapter(messageAdapter);             //adapter set here so list view updates right after a message is sent

                            messageAdapter.notifyDataSetChanged();

                            lvMessages.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);       //used to make the added message at the bottom of the list
                            lvMessages.setStackFromBottom(true);                                        // view and scrolls up when a new item is added

                            message.getText().clear();                      //clears message edittext when message is entered


                        }catch (MessageException e){

                            Toast.makeText(MessageActivity.this, "Invalid message ", Toast.LENGTH_SHORT).show();

                        }

                    }
                }
            });

            lvMessages.setAdapter(messageAdapter);             //this code needs to be repeated so all the messages show up and are scrolled to the very bottom.
            messageAdapter.notifyDataSetChanged();             //if not, then it is just a blank list view until a new message is input and then all the previous
                                                               //messages show up



            lvMessages.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);       //used to make the added message at the bottom of the list
            lvMessages.setStackFromBottom(true);                                        // view and scrolls up when a new item is added

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
            if(message.length()>255){
                returnText = "message cannot excede 256 characters";
                returnVal = false;
            }


            if(!returnVal) {
                Toast toast = Toast.makeText(MessageActivity.this, returnText, Toast.LENGTH_SHORT);
                toast.show();
            }

            return returnVal;
        }

    @Override
    public void onBackPressed() {           //used to go back to previous activity when back button is pressed
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




}