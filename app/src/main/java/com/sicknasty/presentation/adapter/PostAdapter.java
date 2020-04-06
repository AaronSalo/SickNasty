package com.sicknasty.presentation.adapter;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.sicknasty.business.AccessPosts;
import com.sicknasty.objects.*;
import com.sicknasty.R;
import com.sicknasty.presentation.LoggedUserPageActivity;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class  PostAdapter extends ArrayAdapter<Post> {
    private int resourceId;

    AccessPosts posts = new AccessPosts(); //get a reference to posts

    LoggedUserPageActivity pageActivity;
    Post post;
    private final int MAX_COMMENTS_PER_POST = 3; //how many comments should we show per post

    public PostAdapter(@NonNull Context context, int resource , List<Post> posts) {
        super(context,resource,posts);
        pageActivity = (LoggedUserPageActivity) context;
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

            //set up the viewHolder
            viewHolder.ivImage =  view.findViewById(R.id.ivImage);
            viewHolder.userName = view.findViewById(R.id.userName);
            viewHolder.textView = view.findViewById(R.id.textView);
            viewHolder.commentView = view.findViewById(R.id.commentView);
            viewHolder.commentButton = view.findViewById(R.id.commentButton);
            viewHolder.commentEditText = view.findViewById(R.id.commentEditText);
            view.setTag(viewHolder);


            //set up the button listener
            //this will handle what happens when the "comment" button is pressed
            viewHolder.commentButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //create a comment obj
                    String content = viewHolder.commentEditText.getText().toString(); //get the contents of the comment

                    Comment newComment = new Comment(pageActivity.currUser, content, post.getPostID());
                    viewHolder.commentEditText.getText().clear(); //remove the text
                    //increase the size of the container
                    ViewGroup.LayoutParams params = viewHolder.commentView.getLayoutParams();
                    params.height += 100;
                    viewHolder.commentView.setLayoutParams(params);
                    //add the comment to the db
                    posts.addComment(newComment);
                    //update the commentView to include the comment
                    viewHolder.commentView.setText(viewHolder.commentView.getText() + "\n" +
                            pageActivity.currUser.getUsername() + ": " + newComment.getContent());
                }
            });

        }else{
            view=convertView;
            viewHolder=(ViewHolder) view.getTag();
        }

        post = getItem(getCount()-position-1);           //give a post position in layout(now it displays the most recent one)


        //get the path from the post and display it
        //lucas check the following code(setImageUri accepts an URI)

        Uri postUri;
        if (post != null) {
            postUri = Uri.parse(post.getPath());
            viewHolder.ivImage.setImageURI(postUri);             //this is working
            viewHolder.userName.setText(post.getUserId().getUsername());
            viewHolder.textView.setText(post.getText());
            //display the comments

            //first get all the comments on the post from db
            ArrayList<Comment> comments = posts.getComments(post);

            if(!comments.isEmpty()) { //if we have comments on the post

                String commentText = "";
                //insert the text from all the comments into a string
                for (int i = 0; i < comments.size(); i++) {
                    String userName = comments.get(i).getUser().getUsername(); //get the user who posted the comment
                    String commentContent = comments.get(i).getContent(); //get the comment itself
                    commentText += userName + ": " + commentContent + "\n"; //add it to the total comment
                    //increase the size of the container
                    ViewGroup.LayoutParams params = viewHolder.commentView.getLayoutParams();
                    params.height += 100;
                    viewHolder.commentView.setLayoutParams(params);
                }

                //set the commentViews text
                viewHolder.commentView.setText(commentText); //set the text in the xml
            } else { //no comments... reduce the width of the comment view
                viewHolder.commentView.setWidth(1);
            }
        }

        return view;
    }


}//PostAdapter
class ViewHolder{
    ImageView ivImage;
    TextView textView;
    TextView userName;
    TextView commentView;
    EditText commentEditText;
    Button commentButton;
}

