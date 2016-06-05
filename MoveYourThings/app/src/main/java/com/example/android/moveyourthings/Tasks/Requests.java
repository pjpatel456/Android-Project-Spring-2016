package com.example.android.moveyourthings.Tasks;

import android.net.ParseException;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class Requests {

    public static String postRequest(String url, List<NameValuePair> data) {

        String result = "";
        System.out.println("Url " + url + " Data is " + data);

        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new UrlEncodedFormEntity(data));

            HttpClient httpClient = new DefaultHttpClient();
            BasicHttpResponse httpResponse = (BasicHttpResponse) httpClient
                    .execute(httpPost);

            Log.i("response", String.valueOf(httpResponse));

            HttpEntity entity = httpResponse.getEntity();

            if (entity != null) {
                result = EntityUtils.toString(entity);
                result = result.trim();
            }
            Log.i("result", result);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            return e.toString();
        }
        return result;
    }
}
