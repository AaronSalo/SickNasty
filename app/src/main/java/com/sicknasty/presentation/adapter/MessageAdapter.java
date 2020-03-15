package com.sicknasty.presentation.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sicknasty.R;
import com.sicknasty.objects.Message;

import java.util.List;

public class MessageAdapter extends ArrayAdapter<Message> {

    private int resourceId;

    public MessageAdapter(@NonNull Context context, int resource , List<Message> posts) {
        super(context,resource,posts);
        resourceId = resource;

    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;
        final MessageView messageHolder;
        if(convertView==null){
            messageHolder = new MessageView();
            view = LayoutInflater.from(getContext()).inflate(//convertView is null represent layout is not loaded, and it mean that getView is not called
                    resourceId, null);

            messageHolder.msg = view.findViewById(R.id.message_body);

            view.setTag(messageHolder);
        }else{
            view=convertView;
            messageHolder=(MessageView) view.getTag();
        }

        Message message = getItem(position);           //give a post position in layout(now it displays the most recent one)


        if(message!=null) {
            messageHolder.msg.setText(message.getMsg());
        }
        return view;
    }

}

class MessageView{
    TextView msg;
}


