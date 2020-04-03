package com.sicknasty.presentation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sicknasty.R;
import com.sicknasty.business.AccessPages;
import com.sicknasty.business.AccessUsers;
import com.sicknasty.objects.CommunityPage;
import com.sicknasty.objects.Exceptions.UserNotFoundException;
import com.sicknasty.objects.Page;
import com.sicknasty.objects.User;
import com.sicknasty.persistence.exceptions.DBPageNameExistsException;
import com.sicknasty.persistence.exceptions.DBPageNameNotFoundException;
import com.sicknasty.persistence.exceptions.DBUsernameNotFoundException;

public class CreateCommunityActivity extends AppCompatActivity {
    AccessPages pages = new AccessPages();
    AccessUsers users = new AccessUsers();
    private User currUser = null;
    private SharedPreferences preferences = null;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_community);
        final EditText inputName = findViewById(R.id.communityNameAdder);
        Button createCommunityButton = findViewById(R.id.addCommunity);

        preferences = getSharedPreferences("MY_PREFS", MODE_PRIVATE);
        final String loggerInUser =  preferences.getString("username",null);

        try {
            currUser = users.getUser(loggerInUser);

        } catch (UserNotFoundException | DBUsernameNotFoundException e) {

        }

        createCommunityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currName = inputName.getText().toString();

                Page newPage = new CommunityPage(currUser,currName);
                try {
                    pages.insertNewPage(newPage);
                } catch (DBPageNameExistsException e) {
                    Toast.makeText(CreateCommunityActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(CreateCommunityActivity.this, CommunityListPageActivity.class);
                startActivity(intent);
            }
        });
    }
}
