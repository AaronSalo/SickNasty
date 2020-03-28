package com.sicknasty.presentation.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sicknasty.R;
import com.sicknasty.objects.Page;

import java.util.List;

public class CommunityListAdapter extends ArrayAdapter<Page> {
    private int resourceId;

    public CommunityListAdapter(@NonNull Context context, int resource , List<Page> pages) {
        super(context,resource,pages);
        resourceId = resource;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;
        Page page = getItem(getCount() - position -1);

        view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        TextView nameView = (TextView)view.findViewById(R.id.communityTag);
        nameView.setText(page.getPageName());

        return view;
    }
}



