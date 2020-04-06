package com.sicknasty.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.sicknasty.R;
import com.sicknasty.business.AccessPages;
import com.sicknasty.business.AccessUsers;

import java.util.ArrayList;

public class CommunityListPageActivity extends AppCompatActivity {
    AccessUsers users;
    AccessPages pages;
    ArrayAdapter<String> adapter;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_list_page);

        ImageButton createCommunityButton = findViewById(R.id.createCommunityButton);

        users = new AccessUsers();
        pages = new AccessPages();
        lv = findViewById(R.id.communityList);

        ArrayList<String> allCommunityNames = pages.getAllCommunityPages();
        adapter = new ArrayAdapter<>(CommunityListPageActivity.this, android.R.layout.simple_list_item_1, allCommunityNames);

        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CommunityListPageActivity.this, CommunityPageActivity.class);
                intent.putExtra("currentCommunityPage", lv.getItemAtPosition(position).toString());
                startActivity(intent);
                finish();
            }
        });

        createCommunityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CommunityListPageActivity.this, CreateCommunityActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
