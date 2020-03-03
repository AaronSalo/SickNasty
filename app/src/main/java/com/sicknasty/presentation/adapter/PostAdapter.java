package com.sicknasty.presentation.adapter;

import android.net.Uri;
import android.util.Log;
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

public class PostAdapter extends ArrayAdapter<Post> {
    private int resourceId;

    public PostAdapter(@NonNull Context context, int resource , List<Post> posts) {
        super(context,resource,posts);
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

            viewHolder.ivImage =  view.findViewById(R.id.ivImage);
            viewHolder.userName = view.findViewById(R.id.userName);
            viewHolder.textView = view.findViewById(R.id.textView);
            viewHolder.likes = view.findViewById(R.id.likes);

            view.setTag(viewHolder);
        }else{
            view=convertView;
            viewHolder=(ViewHolder) view.getTag();
        }

        Post post =getItem(position);           //give a post position in layout


        //get the path from the post and display it
        //lucas check the following code   (setImageUri accepts an URI)

        Uri postUri =Uri.parse(post.getPath());
        viewHolder.ivImage.setImageURI(postUri);            //will update this for video later en just stick to images

        viewHolder.userName.setText(post.getUserId().getUsername());
        viewHolder.textView.setText(post.getText());
        viewHolder.likes.setText(post.getNumberOfLikes());
        return view;
    }

    public void incrementLikes(){
        this.incrementLikes();
    }



}
class ViewHolder{
    ImageView ivImage;
    TextView textView;
    TextView userName;
    TextView likes;
}

