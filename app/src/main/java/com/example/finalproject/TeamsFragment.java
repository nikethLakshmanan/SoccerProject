package com.example.finalproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeamsFragment extends Fragment implements AsyncResponse {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private EditText enterTeam;
    private String currentSearch;
    private Button search;
    private TextView wins;
    private TextView losses;
    private TextView draws;
    private TextView teamLeague;
    private TextView goalsScored;
    private TextView goalsConcdeded;
    private ListView listView;
    private List<Team> teamList;
    private String JSONString;
    private JSONObject stuff;

    public TeamsFragment() {
        // Required empty public constructor
    }

    public static TeamsFragment newInstance(String param1, String param2) {
        TeamsFragment fragment = new TeamsFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView =  inflater.inflate(R.layout.fragment_teams, container, false);
        enterTeam = fragmentView.findViewById(R.id.enterTeam);
        currentSearch = enterTeam.getText().toString();
        search = fragmentView.findViewById(R.id.search);
        wins = fragmentView.findViewById(R.id.wins);
        losses = fragmentView.findViewById(R.id.losses);
        draws = fragmentView.findViewById(R.id.draws);
        teamLeague = fragmentView.findViewById(R.id.teamLeague);
        goalsScored = fragmentView.findViewById(R.id.goalsScored);
        goalsConcdeded = fragmentView.findViewById(R.id.goalsConceded);
        listView = fragmentView.findViewById(R.id.leaguelistView);
        teamList = new ArrayList<>();


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    DataGetter dataGetter = new DataGetter();
                    dataGetter.response = TeamsFragment.this;
                    dataGetter.action = 0;
                    dataGetter.execute("https://v2.api-football.com/teams/search/" + enterTeam.getText().toString());
                }
                catch (Exception e){

                }
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DataGetter dataGetter = new DataGetter();
                dataGetter.response = TeamsFragment.this;
                dataGetter.action=1;
                dataGetter.context = new HashMap<>();
                dataGetter.context.put("teamId", teamList.get(position).getTeamID());
                dataGetter.execute("https://v2.api-football.com/leagues/team/" +teamList.get(position).getTeamID());
            }
        });



        return fragmentView;
    }
    public void processFinish(int action, Map<String,Object> context, JSONObject output) {
        try {
            if (action == 0) {
                System.out.println("output:" + output);
                JSONArray teams = output.getJSONObject("api").getJSONArray("teams");

                teamList.clear();
                for (int i = 0; i < teams.length(); i++) {
                    JSONObject currentObject = teams.getJSONObject(i);
                    teamList.add(new Team(currentObject.getString("name"), currentObject.getString("team_id"), currentObject.getString("country"), currentObject.getString("venue_name"), currentObject.getString("logo")));
                }

                CustomAdapter_Team customAdapter_team= new CustomAdapter_Team(this.getContext(), R.layout.custom_adapter_teams, teamList);
                listView.setAdapter(customAdapter_team);

            }
            if(action == 1){

                JSONArray leagues = output.getJSONObject("api").getJSONArray("leagues");


                if(leagues.length()>1){
                    teamLeague.setText("League: " + leagues.getJSONObject(0).getString("name"));
                    DataGetter dataGetter = new DataGetter();
                    dataGetter.action=2;
                    dataGetter.execute("https://v2.api-football.com/statistics/" + leagues.getJSONObject(0).getString("league_id")+ "/"+ context.get("teamId") );
                    JSONObject stats = dataGetter.get();
                    System.out.println("Wins: "+stats.getJSONObject("api").getJSONObject("statistics").getJSONObject("matchs").getJSONObject("wins").getInt("total"));
                    wins.setText("Wins: "+stats.getJSONObject("api").getJSONObject("statistics").getJSONObject("matchs").getJSONObject("wins").getInt("total"));
                    draws.setText("Draws: "+stats.getJSONObject("api").getJSONObject("statistics").getJSONObject("matchs").getJSONObject("draws").getInt("total"));
                    losses.setText("Losses: "+stats.getJSONObject("api").getJSONObject("statistics").getJSONObject("matchs").getJSONObject("loses").getInt("total")) ;
                    goalsScored.setText("Goals Scored: "+stats.getJSONObject("api").getJSONObject("statistics").getJSONObject("goals").getJSONObject("goalsFor").getInt("total"));
                    goalsConcdeded.setText("Goals Conceded: "+stats.getJSONObject("api").getJSONObject("statistics").getJSONObject("goals").getJSONObject("goalsAgainst").getInt("total"));

                }
            }

        }catch(Exception e){

        }
    }

}
