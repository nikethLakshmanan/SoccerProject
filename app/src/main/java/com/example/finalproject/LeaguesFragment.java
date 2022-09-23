package com.example.finalproject;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;


public class LeaguesFragment extends Fragment implements AsyncResponse{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private List<Fixture> leagueList  = new ArrayList<Fixture>();


    private String mParam1;
    private String mParam2;
    private Spinner spinner;
    private Button search;
    private ListView listView;
    private int leagueID;
    private ImageView leagueLogo;
    private ArrayList<String> leagues = new ArrayList<String>();

    public LeaguesFragment() {
        // Required empty public constructor
    }


    public static LeaguesFragment newInstance(String param1, String param2) {
        LeaguesFragment fragment = new LeaguesFragment();
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
        View fragmentView =  inflater.inflate(R.layout.fragment_leagues, container, false);

         spinner = fragmentView.findViewById(R.id.spinnerLeagues);
         search = fragmentView.findViewById(R.id.search);
         listView = fragmentView.findViewById(R.id.leaguelistView);
         leagueLogo = fragmentView.findViewById(R.id.leagueLogo);

        leagues.clear();

         leagues.add("Premier League (England)"); //524
         leagues.add("La Liga (Spain)"); // 30
         leagues.add("Bundesliga 1 (Germany)"); //8
         leagues.add("Serie A (Italy)"); //28
         leagues.add("Ligue 1 (France)"); //4
         leagues.add("Primeira Liga (Portugal)"); //11
         leagues.add("Liga MX (Mexico)"); //297
         leagues.add("Eredivisie (Holland)"); //10
         leagues.add("Brasileiro Série A (Brazil)"); //6
         leagues.add("Super Lig (Turkey)"); //112
         leagues.add("Major League Soccer (USA)"); //199

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, leagues);
        spinner.setAdapter(arrayAdapter);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String chosen = spinner.getSelectedItem().toString();
                //System.out.println(chosen);
                switch (chosen){

                    case "Premier League (England)":
                        leagueID = 524;
                        break;
                    case "La Liga (Spain)":
                        leagueID = 30;
                        break;
                    case "Bundesliga 1 (Germany)":
                        leagueID = 8;
                        break;
                    case "Serie A (Italy)":
                        leagueID = 28;
                        break;
                    case "Ligue 1 (France)":
                        leagueID = 4;
                        break;
                    case "Primeira Liga (Portugal)":
                        leagueID = 11;
                        break;
                    case "Liga MX (Mexico)":
                        leagueID = 297;
                        break;
                    case "Eredivisie (Holland)":
                        leagueID = 10;
                        break;
                    case "Brasileiro Série A (Brazil)":
                        leagueID = 6;
                        break;
                    case "Super Lig (Turkey)":
                        leagueID = 112;
                        break;
                    case "Major League Soccer (USA)":
                        leagueID = 199;
                        break;
                }
                DataGetter dataGetter = new DataGetter();
                dataGetter.response = LeaguesFragment.this;
                dataGetter.action = 0;
                dataGetter.execute("https://v2.api-football.com/fixtures/league/" + leagueID);


            }
        });

        return fragmentView;
    }
    public void processFinish(int action, Map<String,Object> context, JSONObject output) {
        try {
            if (action == 0) {

                JSONArray fixtures = output.getJSONObject("api").getJSONArray("fixtures");
                String leagueURL = fixtures.getJSONObject(0).getJSONObject("league").getString("logo");
                leagueList.clear();

                for (int i = fixtures.length()-1; i >=0; i--) {
                    JSONObject currentObject = fixtures.getJSONObject(i);
                    if(currentObject.getString("status").equals("Match Postponed") || currentObject.getString("status").equals("Not Started")){

                    }
                    else {
                         Fixture current = new Fixture(currentObject.getInt("event_timestamp"), currentObject.getJSONObject("homeTeam").getString("team_name"), currentObject.getJSONObject("awayTeam").getString("team_name"), currentObject.getInt("goalsHomeTeam"),
                                currentObject.getInt("goalsAwayTeam"), currentObject.getJSONObject("homeTeam").getString("logo"), currentObject.getJSONObject("awayTeam").getString("logo"), currentObject.getString("venue"), currentObject.getString("status"));
                        leagueList.add(current);
                    }


                }



                ImageGetter imageGetter = new ImageGetter();
                imageGetter.execute(leagueURL);
                try {
                    Drawable drawable = imageGetter.get();
                    leagueLogo.setImageDrawable(drawable);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                CustomAdapter_League customAdapter_league= new CustomAdapter_League(this.getContext(), R.layout.custom_adapter_league , leagueList);
                listView.setAdapter(customAdapter_league);

            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
