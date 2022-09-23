package com.example.finalproject;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.ImageView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageGetter extends AsyncTask<String, Void, Drawable> {

    public ImageView imageView;
    @SuppressLint("WrongThread")
    protected Drawable doInBackground(String... url)  {
        try {
            URL statsURL = new URL(url[0]);
            HttpURLConnection connection = (HttpURLConnection) statsURL.openConnection();

            if (connection.getResponseCode() == 200) {
                InputStream stream = connection.getInputStream();
                Drawable drawable = Drawable.createFromStream(stream, "src name");
                return drawable;
            } else {
                throw new RuntimeException("Error opening url:" + url + ". received status code:" + connection.getResponseCode());

            }
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

}
