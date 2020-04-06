package com.sicknasty.presentation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sicknasty.R;
import com.sicknasty.business.AccessPages;
import com.sicknasty.business.AccessUsers;
import com.sicknasty.objects.CommunityPage;
import com.sicknasty.objects.Exceptions.InvalidCommunityPageNameException;
import com.sicknasty.objects.Exceptions.UserNotFoundException;
import com.sicknasty.objects.Page;
import com.sicknasty.objects.User;
import com.sicknasty.persistence.exceptions.DBPageNameExistsException;
import com.sicknasty.persistence.exceptions.DBUsernameNotFoundException;

public class CreateCommunityActivity extends AppCompatActivity {
    private User currUser = null;
    private AccessPages pages;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_community);

        final EditText inputName = findViewById(R.id.communityNameAdder);
        Button createCommunityButton = findViewById(R.id.addCommunity);

        pages = new AccessPages();
        AccessUsers users = new AccessUsers();

        SharedPreferences preferences = getSharedPreferences("MY_PREFS", MODE_PRIVATE);
        final String loggerInUser = preferences.getString("username",null);

        try {
            currUser = users.getUser(loggerInUser);
        } catch (DBUsernameNotFoundException e) {
            Toast.makeText(CreateCommunityActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        createCommunityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currName = inputName.getText().toString();

                Page newPage;
                try {
                    newPage = new CommunityPage(currUser,currName);
                    pages.insertNewPage(newPage);
                } catch (InvalidCommunityPageNameException | DBPageNameExistsException e) {
                    Toast.makeText(CreateCommunityActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                Intent createCommunityIntent = new Intent(CreateCommunityActivity.this, CommunityListPageActivity.class);
                startActivity(createCommunityIntent);
                finish();
            }
        });
    }
}
