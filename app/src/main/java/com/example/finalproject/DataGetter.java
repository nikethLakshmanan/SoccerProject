package com.example.finalproject;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

public class DataGetter extends AsyncTask<String, Void, JSONObject> {

    public static final String API_KEY ="b294ac89374840ef66f0c5d1c135462e";
    public static final String API_HEADER = "X-RapidAPI-Key";

    public AsyncResponse response = null;
    public int action=-1;
    public Map<String,Object> context = null;

    protected JSONObject doInBackground(String... url)  {
        try {
            URL statsURL = new URL(url[0]);
            HttpURLConnection connection = (HttpURLConnection) statsURL.openConnection();
            connection.setRequestProperty(API_HEADER, API_KEY);

            if (connection.getResponseCode() == 200) {
                InputStream stream = connection.getInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader((stream)));
                String data = reader.readLine();
                return new JSONObject(data);
            } else {
                throw new RuntimeException("Error opening url:" + url + ". received status code:" + connection.getResponseCode());

            }
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void onPostExecute(JSONObject result) {
        if(response!=null)
            response.processFinish(action, context, result);
    }

}
