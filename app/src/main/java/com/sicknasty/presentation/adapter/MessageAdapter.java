package com.sicknasty.presentation.adapter;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sicknasty.R;
import com.sicknasty.objects.Message;
import com.sicknasty.objects.User;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MessageAdapter extends BaseAdapter {


    private Context context1;               //used to connect layouts
    private List<Message>  messages1;       //array of messages
    private User user;                      //user being sent messages
    private User loggedin;                  //user sending messages


    public MessageAdapter(@NonNull Context context, List<Message> messages, User curUser, User loggedInUser) {
        context1 = context;
        messages1 = messages;
        user = curUser;
        loggedin = loggedInUser;
        Log.d("message num: ", Integer.toString(messages.size()));

    }

    @Override
    public int getCount() {                     //needed to extend base adapter
        return messages1.size();
    }

    @Override
    public Object getItem(int position) {                     //needed to extend base adapter
        return messages1.get(position);
    }

    @Override
    public long getItemId(int position) {                     //needed to extend base adapter
        return position;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Message messageHold = messages1.get(position);
            LayoutInflater mInflater = (LayoutInflater) context1.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);



            if(messageHold.getMessenger().getName().equals(loggedin.getName())){
                convertView = mInflater.inflate(R.layout.messagereceived,
                        null);

                TextView name = (TextView) convertView.findViewById(R.id.name_msg);     //adds names of other user above their messages (useful for group chat if implemented)
                name.setText(messageHold.getMessenger().getName());

//                String uri = givenuri;
//                Uri urii = Uri.parse(uri);
//                ImageView profile = convertView.findViewById(R.id.profilePic);        //for profile pic if added
//                profile.setImageURI(urii);

            }else if(messageHold.getMessenger().getName().equals(user.getName())){
                convertView = mInflater.inflate(R.layout.messagesent,
                        null);
            }


        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        Date resultdate = new Date(messageHold.getTimeSent());



        //Log.d("Message: ",messageHold.getMsg());
       // Log.d("|Time sent message\n\n",sdf.format(resultdate));


        TextView msg = (TextView) convertView.findViewById(R.id.message_body);         //adds whatever is in edittext to the text view of the message layout
        msg.setText(messageHold.getMsg());


        TextView time = (TextView) convertView.findViewById(R.id.timeStamp);
        time.setText(resultdate.toString());



        return convertView;
    }
}


