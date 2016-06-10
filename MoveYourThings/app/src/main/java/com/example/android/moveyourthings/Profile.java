package com.example.android.moveyourthings;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.moveyourthings.Tasks.GetProfile;
import com.example.android.moveyourthings.Utility.Constant;

import java.util.ArrayList;


public class Profile extends Fragment {

    Context mContext;

    public Profile(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View view = inflater.inflate(R.layout.profile, container, false);

            GetProfile getP = new GetProfile(mContext, Constant.GET_USER_ID_URL);
            getP.execute(Constant.random_id);
            Intent it = getActivity().getIntent();
            final ArrayList<String> list = it.getStringArrayListExtra(Constant.random_id);
            String sname = list.get(0);
            String semail = list.get(1);
            String saddress = list.get(2);
            String snumber = list.get(3);
            System.out.println(sname + "\n" + semail + "\n" + saddress);
            EditText n = (EditText) view.findViewById(R.id.name);
            EditText e = (EditText) view.findViewById(R.id.email);
            EditText ad = (EditText) view.findViewById(R.id.address);
            EditText number1 = (EditText) view.findViewById(R.id.number);
            n.setText(sname);
            e.setText(semail);
            ad.setText(saddress);
            number1.setText(snumber);

            Button save = (Button) view.findViewById(R.id.share);

            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final
                    EditText n = (EditText) v.findViewById(R.id.name);
                    EditText e = (EditText) v.findViewById(R.id.email);
                    EditText ad = (EditText) v.findViewById(R.id.address);
                    EditText number1 = (EditText) v.findViewById(R.id.number);
                    GetProfile getP = new GetProfile(mContext, Constant.GET_USER_ID_URL);
                    String name = n.toString();
                    String email = e.toString();
                    String address = ad.toString();
                    String number = number1.toString();
                   getP.execute(name,email,address,number);

                    Toast.makeText(mContext,"Saved!",1000);

                    Intent intent = new Intent("com.example.android.moveyourthings.Home");
                    startActivity(intent);
                }
            });

            return view;
    }
}
