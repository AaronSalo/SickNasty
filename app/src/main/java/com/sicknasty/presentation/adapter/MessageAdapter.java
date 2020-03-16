package com.sicknasty.presentation.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sicknasty.R;
import com.sicknasty.objects.Message;
import com.sicknasty.objects.User;

import java.util.List;

public class MessageAdapter extends BaseAdapter {

    private Context context1;
    private List<Message>  messages1;
    private User user;
    private User loggedin;

    public MessageAdapter(@NonNull Context context, List<Message> messages, User curUser, User loggedInUser) {
        context1 = context;
        messages1 = messages;
        user = curUser;
        loggedin = loggedInUser;

    }
    @Override
    public int getCount() {
        return messages1.size();
    }

    @Override
    public Object getItem(int position) {
        return messages1.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Message messageHold = messages1.get(position);
        if(convertView==null){

            LayoutInflater mInflater = (LayoutInflater) context1
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            if(messages1.get(position).getMessenger().getName().equals(loggedin.getName())){
                convertView = mInflater.inflate(R.layout.messagereceived,
                        null);
            }else if(messages1.get(position).getMessenger().getName().equals(user.getName())){
                convertView = mInflater.inflate(R.layout.messagesent,
                        null);
            }else{

            }
        }
        TextView msg = (TextView) convertView.findViewById(R.id.message_body);
        msg.setText(messageHold.getMsg());

        return convertView;
    }
}


