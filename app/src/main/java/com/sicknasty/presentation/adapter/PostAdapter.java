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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import static androidx.constraintlayout.widget.Constraints.TAG;

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
        final ViewHolder viewHolder;
        if(convertView==null){
            viewHolder=new ViewHolder();
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);

            viewHolder.ivImage =  view.findViewById(R.id.ivImage);
            viewHolder.userName = view.findViewById(R.id.userName);
            viewHolder.textView = view.findViewById(R.id.textView);

            view.setTag(viewHolder);
        }else{
            view=convertView;
            viewHolder=(ViewHolder) view.getTag();
        }

        Post post =getItem(getCount()-position-1);           //give a post position in layout(now it displays the most recent one)

        Uri postUri;
        if (post != null) {

            //*To professor/any member*:
            //this is just for some data that's already there and we don't know where to put the uri so
            //when the sample post was created,it was created with a uri of 'test' so we know
            //thanks -Jay

            //ps i asked you about this in iteration 2 and you said it would be okay to do this
            if(post.getPath().equals("test")){
                viewHolder.ivImage.setImageResource(R.drawable.logo);
            }
            else{
                postUri = Uri.parse(post.getPath());
                viewHolder.ivImage.setImageURI(postUri);             //this is working
            }
            viewHolder.userName.setText(post.getPageId().getPageName());            //userid-> pageName due to community page Name
            viewHolder.textView.setText(post.getText());
        }

        return view;
    }
}
class ViewHolder{
    ImageView ivImage;
    TextView textView;
    TextView userName;
}
