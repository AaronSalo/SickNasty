package com.sicknasty.presentation;

import android.os.Bundle;
import android.widget.ListView;

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
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_list_page);

        lv = (ListView) findViewById(R.id.communityList);
        List<Page> lvPage = new ArrayList<Page>();


        AccessUsers users = new AccessUsers();
        String currUserName = null;
        User currUser = null;
        String loggedInUser = getSharedPreferences("MY_PREFS", MODE_PRIVATE).getString("username", null);

        currUserName = loggedInUser;

        try {
            currUser = users.getUser(currUserName);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        } catch (DBUsernameNotFoundException e) {
            e.printStackTrace();
        }

        lvPage.add(new CommunityPage("Manitoba"));
        lvPage.add(new CommunityPage("AAA"));
        lvPage.add(new CommunityPage("BBB"));


        CommunityListAdapter adapter = new CommunityListAdapter(this, R.layout.activity_community_tag, lvPage);
        if (adapter != null) {
            System.out.println("true!!!!!!");
        } else {
            System.out.println("false!!!!!!!");
        }
        lv.setAdapter(adapter);
    }
}
