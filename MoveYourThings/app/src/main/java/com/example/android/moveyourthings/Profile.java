package com.example.android.moveyourthings;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.example.android.moveyourthings.Tasks.GetProfile;
import com.example.android.moveyourthings.Utility.Constant;

import java.util.ArrayList;


public class Profile extends Activity {

    Context mContext;

    public Profile(){

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        GetProfile getP = new GetProfile(mContext, Constant.GET_USER_ID_URL);
        getP.execute(Constant.random_id);
        Intent it = getIntent();
        final ArrayList<String> list = it.getStringArrayListExtra(Constant.random_id);
        String sname = list.get(0);
        String semail = list.get(1);
        String saddress = list.get(2);
        System.out.println(sname+"\n"+semail+"\n"+saddress);
        EditText n =  (EditText)findViewById(R.id.name);
        EditText e =  (EditText)findViewById(R.id.email);
        EditText ad =  (EditText)findViewById(R.id.address);

        n.setText(sname);
        e.setText(semail);
        ad.setText(saddress);

        String name = n.toString();
        if (name=="")
        {
        }


    }

}
