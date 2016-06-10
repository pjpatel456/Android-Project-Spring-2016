package com.example.android.moveyourthings;

import android.content.Context;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.android.moveyourthings.Utility.SqlDatabase;
import com.example.android.moveyourthings.adapter.VideoAdapter;
import com.example.android.moveyourthings.model.VideoListModel;

import java.util.ArrayList;


public class VideoList extends Fragment{

    Context mContext;
    private ListView list_video;
    SqlDatabase sql;
    private ArrayList<VideoListModel> videomodel = new ArrayList<VideoListModel>();
    private VideoAdapter video_adpter;
    private Camera mCamera;

    public VideoList() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (mCamera != null){
            mCamera.release();        // release the camera for other applications
            mCamera = null;
        }
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


        videomodel = sql.getVideoList();
        video_adpter = new VideoAdapter(mContext, videomodel, video_adpter, list_video);
        list_video.setAdapter(video_adpter);
       // uploadvideo();
        return view;
    }


}