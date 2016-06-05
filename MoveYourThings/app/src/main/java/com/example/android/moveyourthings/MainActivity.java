package com.example.android.moveyourthings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.android.moveyourthings.Tasks.GetUserId;
import com.example.android.moveyourthings.Utility.Constant;

public class MainActivity extends AppCompatActivity {

    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = MainActivity.this;
        Constant.random_id = Installation.id(mContext);

        if(Constant.user_id == ""){
            GetUserId getUserId = new GetUserId(mContext, Constant.GET_USER_ID_URL);
            getUserId.execute(Constant.random_id);
        }

        Button userBtn = (Button) findViewById(R.id.moving);
        userBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Constant.user_id == ""){
                    GetUserId getUserId = new GetUserId(mContext, Constant.GET_USER_ID_URL);
                    getUserId.execute(Constant.random_id);
                }
                Intent intent = new Intent(mContext, UserPage.class);
                startActivity(intent);
                finish();
            }
        });
    }


}
