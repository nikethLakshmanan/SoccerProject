package com.example.finalproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class PlayerFragment extends Fragment implements AsyncResponse {

    private EditText enterPlayer;
    private String currentSearch;
    private Button search;
    private TextView playerTeam;
    private TextView goals;
    private TextView passes;
    private TextView dribbles;
    private TextView tackles;
    private TextView fouls;
    private ListView listView;
    private List<Player> playerList;
    private String JSONString;
    private JSONObject stuff;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public PlayerFragment() {
        // Required empty public constructor
    }


    public static PlayerFragment newInstance(String param1, String param2) {
        PlayerFragment fragment = new PlayerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }






    }
    

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView =  inflater.inflate(R.layout.fragment_player, container, false);
        enterPlayer  = fragmentView.findViewById(R.id.enterTeam);
        currentSearch = enterPlayer.getText().toString();
        search = fragmentView.findViewById(R.id.search);
        playerTeam = fragmentView.findViewById(R.id.wins);
        goals = fragmentView.findViewById(R.id.losses);
        passes = fragmentView.findViewById(R.id.draws);
        dribbles = fragmentView.findViewById(R.id.teamLeague);
        tackles = fragmentView.findViewById(R.id.goalsScored);
        fouls = fragmentView.findViewById(R.id.goalsConceded);
        listView = fragmentView.findViewById(R.id.leaguelistView);
        playerList = new ArrayList<>();





        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    DataGetter dataGetter = new DataGetter();
                    dataGetter.response=PlayerFragment.this;
                    dataGetter.action=0;
                    dataGetter.execute("https://v2.api-football.com/players/search/" + enterPlayer.getText().toString());
                    Log.d("hereYEET", ""+stuff.toString());
                    Log.d("here",enterPlayer.getText().toString());







                }
                catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Player current = playerList.get(position);
                int currentID = current.getPlayerID();

                try{
                    DataGetter dataGetter = new DataGetter();
                    dataGetter.response=PlayerFragment.this;
                    dataGetter.action=1;
                    dataGetter.execute("https://v2.api-football.com/players/player/"+currentID);

                }
                catch (Exception e){

                }

            }
        });


        return fragmentView;

    }

    public void processFinish(int action, Map<String,Object> context, JSONObject output) {
        try {
            if (action == 0) {
                System.out.println("output:" + output);
                JSONArray players = output.getJSONObject("api").getJSONArray("players");

                playerList.clear();
                for (int i = 0; i < players.length(); i++) {
                    JSONObject currentObject = players.getJSONObject(i);
                    playerList.add(new Player(currentObject.getString("player_name"), currentObject.getString("position"), currentObject.getString("nationality"), currentObject.getInt("age"), currentObject.getInt("player_id")));

                }
                CustomAdapter_Player customAdapter_player = new CustomAdapter_Player(this.getContext(), R.layout.custom_adapter_players, playerList);
                listView.setAdapter(customAdapter_player);

            }else if(action == 1){
                JSONArray seasons = output.getJSONObject("api").getJSONArray("players");

                goals.setText("Goals: "+seasons.getJSONObject(0).getJSONObject("goals").getInt("total"));
                playerTeam.setText("Teams: "+seasons.getJSONObject(0).getString("team_name"));
                dribbles.setText("Dribbles: "+seasons.getJSONObject(0).getJSONObject("dribbles").getInt("success"));
                passes.setText("Passes: "+seasons.getJSONObject(0).getJSONObject("passes").getInt("total"));
                tackles.setText("Tackles: "+seasons.getJSONObject(0).getJSONObject("tackles").getInt("total"));
                fouls.setText(("Fouls: "+seasons.getJSONObject(0).getJSONObject("fouls").getInt("committed")));
            }
        }catch(Exception e){

        }
    }


}



