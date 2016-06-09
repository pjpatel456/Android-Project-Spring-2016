package com.example.android.moveyourthings;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.android.moveyourthings.Utility.SqlDatabase;


public class VideoList extends Fragment{

    Context mContext;
    private ListView list_video;
    SqlDatabase sql;

    public VideoList() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.video_list, container, false);

        mContext = view.getContext();
        list_video = (ListView) view.findViewById(R.id.video_list);
        sql = new SqlDatabase(mContext);

        return view;
    }

}