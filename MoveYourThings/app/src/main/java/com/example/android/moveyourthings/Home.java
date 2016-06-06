package com.example.android.moveyourthings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.moveyourthings.Utility.Constant;


public class Home extends Fragment{

    Context mContext;
    static final int REQUEST_VIDEO_CAPTURE = 1;
    public TextView user_id_text;

    public Home() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home, container, false);
        mContext = view.getContext();
        user_id_text = (TextView) view.findViewById(R.id.user_id);
        user_id_text.setText(Constant.user_id);

        Button record = (Button) view.findViewById(R.id.recoding);


        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                if (takeVideoIntent.resolveActivity(v.getContext().getPackageManager()) != null) {
                    startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
                }
            }
        });


        return view;
    }

}