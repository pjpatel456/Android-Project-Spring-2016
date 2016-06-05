package com.example.android.moveyourthings;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

public class UserPage extends Activity {
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userpage);
        mContext = UserPage.this;
    }

}
