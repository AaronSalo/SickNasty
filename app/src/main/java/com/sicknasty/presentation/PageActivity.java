package com.sicknasty.presentation;

import android.os.Bundle;

import com.sicknasty.R;
import com.sicknasty.objects.*;
import com.sicknasty.adapter.PostAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class PageActivity extends AppCompatActivity {

    private ListView lvPost;
    private List<PicturePost> postList = new ArrayList<PicturePost>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvPost = (ListView) findViewById(R.id.lvPost);
        getData();

        PostAdapter postAdapter = new PostAdapter(this,
                R.layout.activity_post, postList);
        lvPost.setAdapter(postAdapter);

        lvPost.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PicturePost post = postList.get(position);
                Toast.makeText(PageActivity.this, post.getText(), Toast.LENGTH_LONG).show();
            }
        });
    }

    //Hard code here for post
    private void getData() {
        User user =  new User("abc","aaa");

        int[] imageIds = {R.drawable.logo, R.drawable.logo,
                R.drawable.logo, R.drawable.logo,
                R.drawable.logo, R.drawable.logo,
                R.drawable.logo, R.drawable.logo,
                R.drawable.logo, R.drawable.logo};
        String[] userName = {"a", "b", "c", "d", "e", "f", "g", "h", "i",
                "j"};
        String[] text = {"a", "b", "c", "d", "e", "f", "g", "h", "i",
                "j"};
        for (int i = 0; i < imageIds.length; i++) {
            postList.add(new PicturePost(text[i],user,imageIds[i],123, 123, 123, user.getPersonalPage());
        }
    }


}
