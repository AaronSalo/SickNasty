package com.sicknasty.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.sicknasty.R;
import com.sicknasty.business.AccessUsers;

public class SearchActivity extends AppCompatActivity {
    SearchView mySearchView;
    AccessUsers users=new AccessUsers();            //for fetching all users
    ListView listOfSearches;
    ArrayAdapter<String> adapter;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mySearchView = findViewById(R.id.search_view);
        listOfSearches = findViewById(R.id.search_user);

        adapter = new ArrayAdapter<>(SearchActivity.this, android.R.layout.simple_list_item_1, users.getUsersByUsername());
        sharedPreferences = getSharedPreferences("MY_PREFS", MODE_PRIVATE);
        final String loggedInUser = sharedPreferences.getString("username",null);

        listOfSearches.setAdapter(adapter);
        listOfSearches.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //proceed according to the current item selected(redirect to pageActivity)
                String user = listOfSearches.getItemAtPosition(position).toString();
                Intent newIntent;
                if(user.equals(loggedInUser)){
                     newIntent = new Intent(SearchActivity.this, LoggedUserPageActivity.class);
                }else{
                     newIntent = new Intent(SearchActivity.this, OtherUserPageActivity.class);
                     //let's keep this if statement for now -Jay
                }
                newIntent.putExtra("user",listOfSearches.getItemAtPosition(position).toString());
                startActivity(newIntent);
            }
        });

        mySearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
    }
}
