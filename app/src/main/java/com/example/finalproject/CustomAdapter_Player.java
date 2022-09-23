package com.example.finalproject;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CustomAdapter_Player extends ArrayAdapter<Player> {
        Context parentContext;
        List<Player> list;

        int xmlRes;

        public CustomAdapter_Player(@NonNull Context context, int resource, @NonNull List<Player> objects){
            super(context, resource, objects);
            parentContext = context;
            list = objects;
            xmlRes = resource;
        }
        @NonNull
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent){
            LayoutInflater layoutInflater = (LayoutInflater)(parentContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE));
            View adapterView = layoutInflater.inflate(xmlRes, parent, false);



            /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    //fill in the texts on fragment_player.xml


                }
            });*/
            TextView customplayers_name = adapterView.findViewById(R.id.customplayer_name);
            customplayers_name.setText(list.get(position).getName());
            TextView customplayers_age = adapterView.findViewById(R.id.customplayer_age);
            try {
                customplayers_age.setText("Age: "+list.get(position).getAge());
            }catch(Exception e){
                customplayers_age.setText("N/A");
            }
            TextView customplayers_nationality = adapterView.findViewById(R.id.customteam_venuename);
            customplayers_nationality.setText(list.get(position).getNationality());
            TextView customplayers_position = adapterView.findViewById(R.id.customteam_name);
            customplayers_position.setText(list.get(position).getPosition());
            return adapterView;


        }
    }

