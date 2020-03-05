package com.sicknasty.presentation;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.sicknasty.R;
import com.sicknasty.business.AccessUsers;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    SearchView mySearchView;
    AccessUsers users=new AccessUsers();            //for fetching all users
    ListView listOfSearches;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
//        setContentView(R.layout.activity_navigation);
        mySearchView=findViewById(R.id.search_view);
        listOfSearches=findViewById(R.id.search_user);

        ArrayList<String> listOfUsers=new ArrayList<>();

        //add all the users from the db

        adapter=new ArrayAdapter<>(SearchActivity.this,android.R.layout.simple_list_item_1,listOfUsers);
        listOfSearches.setAdapter(adapter);
        listOfSearches.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //proceed according to the current item selected(redirect to pageActivity)

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
