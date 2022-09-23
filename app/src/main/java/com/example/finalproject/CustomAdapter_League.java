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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CustomAdapter_League extends ArrayAdapter<Fixture> {
    Context parentContext;
    List<Fixture> list;

    int xmlRes;

    public CustomAdapter_League(@NonNull Context context, int resource, @NonNull List<Fixture> objects){
        super(context, resource, objects);
        parentContext = context;
        list = objects;
        xmlRes = resource;
    }
    @NonNull
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent){
        LayoutInflater layoutInflater = (LayoutInflater)(parentContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE));
        View adapterView = layoutInflater.inflate(xmlRes, parent, false);


        TextView homeScore = adapterView.findViewById(R.id.league_homeScore);
        homeScore.setText(""+list.get(position).getHomeScore());
        TextView awayScore = adapterView.findViewById(R.id.league_awayScore);
        awayScore.setText(""+list.get(position).getAwayScore());
        TextView homeTeam = adapterView.findViewById(R.id.league_homeName);
        homeTeam.setText(list.get(position).getHomeTeam());
        TextView awayTeam = adapterView.findViewById(R.id.league_awayName);
        awayTeam.setText(list.get(position).getAwayTeam());

        TextView date = adapterView.findViewById(R.id.league_date);

        long epoch = list.get(position).getDate();
        Date dateVar = new Date(epoch*1000);
        DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        String temp = format.format(dateVar);
        String[] mainarr = format.format(dateVar).split(" ");
        date.setText(mainarr[0]);

        ImageView homeLogo = adapterView.findViewById(R.id.league_homeLogo);
        String homeURL = list.get(position).getHomeLogo();
        ImageGetter imageGetter = new ImageGetter();
        imageGetter.execute(homeURL);
        try {
            Drawable drawable = imageGetter.get();
            homeLogo.setImageDrawable(drawable);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ImageView awayLogo = adapterView.findViewById(R.id.league_awayLogo);
        String awayURL = list.get(position).getAwayLogo();
        ImageGetter imageGetter1 = new ImageGetter();
        imageGetter1.execute(awayURL);
        try {
            Drawable drawable = imageGetter1.get();
            awayLogo.setImageDrawable(drawable);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        TextView venue = adapterView.findViewById(R.id.league_venue);
        venue.setText(list.get(position).getVenue());

        return adapterView;


    }


}

