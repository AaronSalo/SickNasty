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

import java.util.ArrayList;
import java.util.List;
import android.view.LayoutInflater;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class  PostAdapter extends ArrayAdapter<Post> {
    private int resourceId;
    private final int MAX_COMMENTS_PER_POST = 3; //how many comments should we show per post

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
            view = LayoutInflater.from(getContext()).inflate(//convertView is null represent layout is not loaded, and it mean that getView is not called
                    resourceId, null);

            viewHolder.ivImage =  view.findViewById(R.id.ivImage);
            viewHolder.userName = view.findViewById(R.id.userName);
            viewHolder.textView = view.findViewById(R.id.textView);
            viewHolder.commentView = view.findViewById(R.id.commentView);

            view.setTag(viewHolder);
        }else{
            view=convertView;
            viewHolder=(ViewHolder) view.getTag();
        }

        Post post =getItem(getCount()-position-1);           //give a post position in layout(now it displays the most recent one)


        //get the path from the post and display it
        //lucas check the following code(setImageUri accepts an URI)

        Uri postUri;
        if (post != null) {
            postUri = Uri.parse(post.getPath());
            viewHolder.ivImage.setImageURI(postUri);             //this is working
            viewHolder.userName.setText(post.getUserId().getUsername());
            viewHolder.textView.setText(post.getText());

            ArrayList<Comment> comments = post.getComments();

            if(!comments.isEmpty()) { //if we have comments on the post

                String commentText = "";
                //insert the text from all the comments into a string
                for(int i = 0; i < MAX_COMMENTS_PER_POST; i++) {
                    String userName = comments.get(i).getUser().getUsername(); //get the user who posted the comment
                    String commentContent = comments.get(i).getContent(); //get the comment itself
                    commentText += userName + ": " + commentContent + "\n"; //add it to the total comment
                }

                if(comments.size() > MAX_COMMENTS_PER_POST)
                    commentText += "See all " + comments.size() + " comments..."; //show the user there are more comments we arent showing
                    //TODO add link to Comment activity, where we show all the comments

                //set the commentViews text
                viewHolder.commentView.setText(commentText); //set the text in the xml
            }
        }

        return view;
    }
}
class ViewHolder{
    ImageView ivImage;
    TextView textView;
    TextView userName;
    TextView commentView;
}

