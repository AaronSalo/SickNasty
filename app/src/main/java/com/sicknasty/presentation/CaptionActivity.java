package com.sicknasty.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.sicknasty.R;
import com.sicknasty.business.AccessPages;
import com.sicknasty.business.AccessPosts;
import com.sicknasty.objects.Page;
import com.sicknasty.persistence.exceptions.DBPageNameNotFoundException;

public class CaptionActivity extends AppCompatActivity {

    AccessPosts posts=new AccessPosts();
    AccessPages pages =new AccessPages();
    Page ourPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caption);

        EditText caption=findViewById(R.id.captionText);
        Button postButton=findViewById(R.id.captionPost);
        ImageView imageView=findViewById(R.id.postImage);

        Intent intent=getIntent();
        //fetch the post
        try {
            ourPage=pages.getPage(intent.getStringExtra("pageName"));
        }catch (DBPageNameNotFoundException e){
            String errorMsg = e.getMessage();
            Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_SHORT).show();
        }
        //how can i get the post

        String uri=getIntent().getStringExtra("URI");
        //imageView.setImageURI(Uri.parse(uri));

        final String captionText=caption.getText().toString();
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateInput(captionText)){

                    Intent intent=new Intent(CaptionActivity.this,PageActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private boolean validateInput(String caption){
        String infoText = "";
        boolean result=true;

        if(caption.length()>255){
            infoText = "caption is too long : must be less than 255 characters";
            result = false;
        }

        if(infoText.length() > 0) {
            Toast toast = Toast.makeText(CaptionActivity.this, infoText, Toast.LENGTH_SHORT);
            toast.show();
        }
        return result;
    }
}
