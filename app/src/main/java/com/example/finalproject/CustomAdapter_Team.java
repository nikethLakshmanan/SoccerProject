package com.example.finalproject;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CustomAdapter_Team extends ArrayAdapter<Team> {
    Context parentContext;
    List<Team> list;

    int xmlRes;

    public CustomAdapter_Team(@NonNull Context context, int resource, @NonNull List<Team> objects){
        super(context, resource, objects);
        parentContext = context;
        list = objects;
        xmlRes = resource;
    }
    @NonNull
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent){
        LayoutInflater layoutInflater = (LayoutInflater)(parentContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE));
        View adapterView = layoutInflater.inflate(xmlRes, parent, false);


        TextView customteam_name = adapterView.findViewById(R.id.customplayer_name);
        customteam_name.setText(list.get(position).getName());
        TextView customteam_venuename = adapterView.findViewById(R.id.customteam_venuename);
        if(list.get(position).getVenueName().equals("null")){
            customteam_venuename.setText("No Data Available");
        }
        else {
            customteam_venuename.setText(list.get(position).getVenueName());
        }
        TextView customteam_country = adapterView.findViewById(R.id.customteam_name);
        customteam_country.setText(list.get(position).getCountry());
        ImageView customteam_logo = adapterView.findViewById(R.id.customteam_logo);
        String str = list.get(position).getPictureURL();
        ImageGetter imageGetter = new ImageGetter();
        imageGetter.execute(str);
        try {
            Drawable drawable = imageGetter.get();
            customteam_logo.setImageDrawable(drawable);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return adapterView;


    }


}

