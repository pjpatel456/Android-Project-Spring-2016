package com.example.android.moveyourthings.Tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.example.android.moveyourthings.Utility.Constant;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GetUserId extends AsyncTask<String, Integer, Object>{

    private final Context mContext;


    public GetUserId(final Context mContext, String url) {
        this.mContext = mContext;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Object doInBackground(String... params) {
        return getUserId(params[0]);
    }

    private String getUserId(final String random_id) {

        String webUrl = Constant.GET_USER_ID_URL;

        try {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("random_id", random_id));
            String response = Requests.postRequest(webUrl, nameValuePairs);

            JSONObject jsonObj = new JSONObject(response);
            Constant.user_id = jsonObj.get("user_id").toString();
            return Constant.user_id;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
