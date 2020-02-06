package com.sicknasty.adapter;

import android.widget.ArrayAdapter;

import com.sicknasty.objects.*;
import com.sicknasty.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.List;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

public class PostAdapter extends ArrayAdapter<PicturePost> {
    private int resourceId;

    public PostAdapter(@NonNull Context context, int resource ,List<PicturePost> posts) {
        super(context, resource,posts);
        resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;
        ViewHolder viewHolder;
        if(convertView==null){
            viewHolder=new ViewHolder();
            view = LayoutInflater.from(getContext()).inflate(//convertView is null represent layout is not loaded, and it mean that getView is not called
                    resourceId, null);

            viewHolder.ivImage = (ImageView) view.findViewById(R.id.ivImage);
            //viewHolder.communityName = (TextView) view.findViewById(R.id.communityName);
            viewHolder.userName = (TextView) view.findViewById(R.id.userName);
            viewHolder.textView = (TextView) view.findViewById(R.id.textView);

            view.setTag(viewHolder);
        }else{
            view=convertView;
            viewHolder=(ViewHolder) view.getTag();
        }

        PicturePost post = getItem(position);//give a post position in layout

        viewHolder.ivImage.setImageResource(post.getPICTURE_PATH());
        viewHolder.userName.setText(post.getUserId().getUsername());
        //viewHolder.communityName.setText(post.getCommunityName());
        viewHolder.textView.setText(post.getText());
        return view;

    }
}
class ViewHolder{
    ImageView ivImage;
    TextView textView;
    TextView userName;
    //TextView communityName;
}

