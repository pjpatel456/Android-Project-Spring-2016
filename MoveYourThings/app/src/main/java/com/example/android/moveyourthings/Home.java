package com.example.android.moveyourthings;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.moveyourthings.Utility.Constant;


public class Home extends Fragment{

    Context mContext;
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
        System.out.print(user_id_text);

        user_id_text.setText(Constant.user_id);
        return view;
    }

}