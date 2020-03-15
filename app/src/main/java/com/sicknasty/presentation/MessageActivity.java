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
    ArrayAdapter<Message> adapter;
    MessageAdapter messageAdapter = null;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_chat);

            Intent intent=getIntent();

            Bundle extras;


            extras = intent.getExtras();

            try {
                curUser = users.getUser(extras.getString("currentUser"));
                Log.d("currentUser",(curUser.getName()));
            } catch (UserNotFoundException | DBUsernameNotFoundException e) {
                String errorMsg = e.getMessage();
                Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_SHORT).show();
            }

            try {
                loggedInUser = users.getUser(extras.getString("loggedInUser"));
                Log.d("loggedinuser",(loggedInUser.getName()));
            } catch (UserNotFoundException | DBUsernameNotFoundException e) {
                String errorMsg = e.getMessage();
                Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_SHORT).show();
            }



            final ListView lvMessages = findViewById(R.id.messages_view);      //lists messages
            TextView chatName = findViewById(R.id.chatname);             //chatname at the top of each chat
            ImageButton sendButton = findViewById(R.id.sendMessage);     //button that will send the message after entered
            final EditText message = findViewById(R.id.messageEntered);        //message to be sent to the listview



           // adapter = new ArrayAdapter<>(MessageActivity.this,android.R.layout.simple_list_item_1, users.getMessages(loggedInUser, curUser));


            try {
                    messageAdapter = new MessageAdapter(MessageActivity.this, R.layout.messagereceived,users.getMessages(loggedInUser, curUser));


            } catch (Exception e) {
                Log.d("messageAdapter problem", message.toString());
            }


            lvMessages.setAdapter(messageAdapter);             //adapter to show messages in array list from database
            sendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String messageinput = message.getText().toString();
                    if(validateMsg(messageinput)) {

                        try{
                            Message message1 = new Message(messageinput,loggedInUser,curUser);
                            users.addMessage(message1);
                            Log.d("actual message",(message1.getMsg()));
                            Log.d("inside of database",(users.getMessages(loggedInUser, curUser).get(0).getMsg()));
                            Log.d("user1 Name",(loggedInUser.getName()));
                            Log.d("user2 Name",(curUser.getName()));

                            try {
                                if(message1.getMessenger().getName().equals(loggedInUser.getName())){
                                    messageAdapter = new MessageAdapter(MessageActivity.this, R.layout.messagesent,users.getMessages(loggedInUser, curUser));
                                }else{
                                    messageAdapter = new MessageAdapter(MessageActivity.this, R.layout.messagereceived,users.getMessages(curUser, loggedInUser));

                                }


                            } catch (Exception e) {
                                Log.d("messageAdapter problem", message.toString());
                            }
                            lvMessages.setAdapter(messageAdapter);             //adapter to show messages in array list from database

                            messageAdapter.notifyDataSetChanged();
                            message.getText().clear();

                        }catch (MessageException e){

                            //add exception message

                        }


                    }

                }
            });
            lvMessages.setAdapter(messageAdapter);             //adapter to show messages in array list from database

            messageAdapter.notifyDataSetChanged();




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

}