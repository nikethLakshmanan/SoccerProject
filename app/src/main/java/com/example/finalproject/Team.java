package com.example.finalproject;

public class Team {
    private String name;
    private String teamID;
    private String country;
    private String venueName;
    private String pictureURL;

    public Team(String name, String teamID, String country, String venueName, String pictureURL){
        this.name = name;
        this.teamID = teamID;
        this.country = country;
        this.venueName = venueName;
        this.pictureURL = pictureURL;
    }

    public String getName() {
        return name;
    }

    public String getTeamID() {
        return teamID;
    }

    public String getCountry() {
        return country;
    }

    public String getVenueName() {
        return venueName;
    }

    public String getPictureURL() {
        return pictureURL;
    }
}
