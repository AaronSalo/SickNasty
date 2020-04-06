package com.sicknasty.presentation.adapter;

import android.content.SharedPreferences;
import android.net.Uri;
import android.widget.ArrayAdapter;

import com.sicknasty.business.AccessPosts;
import com.sicknasty.business.AccessUsers;
import com.sicknasty.objects.*;
import com.sicknasty.R;
import com.sicknasty.objects.Exceptions.UserNotFoundException;
import com.sicknasty.persistence.exceptions.DBUsernameNotFoundException;
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
import android.widget.ImageView;
import android.widget.TextView;

import static android.content.Context.MODE_PRIVATE;
import android.widget.ImageView;
import android.widget.TextView;

public class PostAdapter extends ArrayAdapter<Post> {
    private int resourceId;

    //the height of the commentview when no comments have been added
    final int DEFAULT_COMMENT_VIEW_HEIGHT = 10;

    AccessPosts posts = new AccessPosts(); //get a reference to posts

    SharedPreferences pref;

    Post post;

    public PostAdapter(@NonNull Context context, int resource, List<Post> posts) {
        super(context,resource,posts);

        resourceId = resource;

        this.pref = context.getSharedPreferences("MY_PREFS",MODE_PRIVATE);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;
        final ViewHolder viewHolder;

        if (convertView == null){
            LayoutInflater layoutInflate = LayoutInflater.from(getContext());

            view = layoutInflate.inflate(resourceId, null);
            viewHolder = new ViewHolder();

            viewHolder.ivImage = view.findViewById(R.id.ivImage);
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
                    AccessUsers userLogic = new AccessUsers();
                    User loggedInUser = null;

                    try {
                        String loggedInUsername = pref.getString("username", null);
                        loggedInUser = userLogic.getUser(loggedInUsername);

                        //create a comment obj
                        String content = viewHolder.commentEditText.getText().toString(); //get the contents of the comment

                        Comment newComment = new Comment(loggedInUser, content, post.getPostID());
                        viewHolder.commentEditText.getText().clear(); //remove the text

                        //increase the size of the container
                        ViewGroup.LayoutParams params = viewHolder.commentView.getLayoutParams();
                        params.height += 100;
                        viewHolder.commentView.setLayoutParams(params);

                        //add the comment to the db
                        posts.addComment(newComment);

                        //update the commentView to include the comment
                        viewHolder.commentView.setText(viewHolder.commentView.getText() + "\n" +
                                loggedInUser.getUsername() + ": " + newComment.getContent());
                    } catch (UserNotFoundException | DBUsernameNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        Post post = getItem(getCount() - position - 1);           //give a post position in layout(now it displays the most recent one)

        Uri postUri;
        if (post != null) {
            //*To professor/any member*:
            //this is just for some data that's already there and we don't know where to put the uri so
            //when the sample post was created,it was created with a uri of 'test' so we know
            //thanks -Jay

            //ps i asked you about this in iteration 2 and you said it would be okay to do this
            if (post.getPath().equals("test")) {
                viewHolder.ivImage.setImageResource(R.drawable.logo);
            } else {
                postUri = Uri.parse(post.getPath());
                viewHolder.ivImage.setImageURI(postUri);             //this is working
            }

            viewHolder.userName.setText(post.getPageId().getPageName());            //userid-> pageName due to community page Name
            
            viewHolder.textView.setText(post.getText());
            //display the comments

            //first get all the comments on the post from db
            ArrayList<Comment> comments = posts.getComments(post);

            if(!comments.isEmpty()) { //if we have comments on the post

                ViewGroup.LayoutParams params = viewHolder.commentView.getLayoutParams();
                params.height = DEFAULT_COMMENT_VIEW_HEIGHT;
                viewHolder.commentView.setLayoutParams(params);

                String commentText = "";
                //insert the text from all the comments into a string
                for (int i = 0; i < comments.size(); i++) {
                    String userName = comments.get(i).getUser().getUsername(); //get the user who posted the comment
                    String commentContent = comments.get(i).getContent(); //get the comment itself
                    commentText += userName + ": " + commentContent + "\n"; //add it to the total comment
                    //increase the size of the container
                    params = viewHolder.commentView.getLayoutParams();
                    params.height += 100;
                    viewHolder.commentView.setLayoutParams(params);
                }

                //set the commentViews text
                viewHolder.commentView.setText(commentText); //set the text in the xml
            } else { //no comments... reduce the width of the comment view
                ViewGroup.LayoutParams params = viewHolder.commentView.getLayoutParams();
                params.height = DEFAULT_COMMENT_VIEW_HEIGHT;
                viewHolder.commentView.setLayoutParams(params);
            }
        }

        return view;
    }
}//PostAdapter

class ViewHolder {
    ImageView ivImage;
    TextView textView;
    TextView userName;
    TextView commentView;
    EditText commentEditText;
    Button commentButton;
}
