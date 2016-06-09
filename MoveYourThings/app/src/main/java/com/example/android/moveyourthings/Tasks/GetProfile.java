package com.example.android.moveyourthings.Tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.example.android.moveyourthings.Utility.Constant;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GetProfile extends AsyncTask<String, Integer, Object>{

    private final Context mContext;


    public GetProfile(final Context mContext, String url) {
        this.mContext = mContext;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Object doInBackground(String... params) {
        return getProfile(params[0], params[1], params[2], params[3], params[4]);
    }

    private ArrayList<String> getProfile(final String random_id, final String name, final String email,final String address, final String number) {

        ArrayList<String> list = new ArrayList<String>();
        String webUrl = Constant.GET_PROFILE;

        try {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("random_id", random_id));
            nameValuePairs.add(new BasicNameValuePair("name", name));
            nameValuePairs.add(new BasicNameValuePair("email", email));
            nameValuePairs.add(new BasicNameValuePair("address", address));
            nameValuePairs.add(new BasicNameValuePair("number", number));
            String response = Requests.postRequest(webUrl, nameValuePairs);

            JSONObject jsonObj = new JSONObject(response);
            Constant.name = jsonObj.get("name").toString();
            list.add(Constant.name);
            Constant.email = jsonObj.get("email").toString();
            list.add(Constant.email);
            Constant.address = jsonObj.get("address").toString();
            list.add(Constant.address);
            Constant.number = jsonObj.get("address").toString();
            list.add(Constant.number);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
