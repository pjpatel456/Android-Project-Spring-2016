package com.example.android.moveyourthings;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.moveyourthings.Utility.Constant;


public class ShareDetails extends Fragment{

    Context mContext;
    public TextView user_id_text;

    public ShareDetails() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.share, container, false);
        mContext = view.getContext();
        user_id_text = (TextView) view.findViewById(R.id.user_id);
        user_id_text.setText(Constant.user_id);

        Button share = (Button) view.findViewById(R.id.share);

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String to[] = {""};
                Intent email = new Intent(Intent.ACTION_SEND);
                email.setData(Uri.parse("Mailto:"));
                email.setType("text/plain");
                email.putExtra(Intent.EXTRA_EMAIL, to);
                email.putExtra(Intent.EXTRA_SUBJECT, "MYT ID : " + Constant.user_id);
                email.putExtra(Intent.EXTRA_TEXT, "MYT ID : "+ Constant.user_id
                + "\nName : " + Constant.name + "\nNumber : "+ Constant.number );
                startActivity(email);
            }
        });

        return view;
    }

}