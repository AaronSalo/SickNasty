package com.sicknasty.presentation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ShareActionProvider;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.sicknasty.R;
import com.sicknasty.business.AccessUsers;
import com.sicknasty.objects.CommunityPage;
import com.sicknasty.objects.Exceptions.UserNotFoundException;
import com.sicknasty.objects.Page;
import com.sicknasty.objects.User;
import com.sicknasty.persistence.exceptions.DBUsernameNotFoundException;
import com.sicknasty.presentation.adapter.CommunityListAdapter;

import java.util.ArrayList;
import java.util.List;

public class CommunityListPageActivity extends AppCompatActivity {
    ListView lv;
    AccessUsers users = new AccessUsers();
    User loggedInUser = null;
    CommunityListAdapter adapter = null;
    List<Page> lvPage = null;
    User currUser;
    SharedPreferences preferences;
    String clickedCommunityPage = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_list_page);

        lv = (ListView) findViewById(R.id.communityList);
        lvPage = new ArrayList<Page>();

        preferences = getSharedPreferences("MY_PREFS", MODE_PRIVATE);

        final String loggerInUser =  preferences.getString("username",null);

        try {
            currUser = users.getUser(loggerInUser);
            Page p1 = new CommunityPage("Manitoba");
            Page p2 = new CommunityPage("AAA");
            Page p3 = new CommunityPage("BBB");
            lvPage.add(p1);
            lvPage.add(p2);
            lvPage.add(p3);

            if(preferences.getBoolean("isLogin", true)){

            }


        } catch (UserNotFoundException e) {
            e.printStackTrace();
        } catch (DBUsernameNotFoundException e) {
            e.printStackTrace();
        }


        adapter = new CommunityListAdapter(this, R.layout.activity_community_tag, lvPage);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Page page = lvPage.get(lvPage.size() - position -1);
                clickedCommunityPage = page.getPageName();
                Toast.makeText(CommunityListPageActivity.this, page.getPageName(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(CommunityListPageActivity.this, CommunityPageActivity.class);
                intent.putExtra("currentCommunityPage", clickedCommunityPage);
                startActivity(intent);
                finish();
            }
        });

    }
}
