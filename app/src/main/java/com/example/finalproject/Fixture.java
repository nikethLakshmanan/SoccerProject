package com.example.finalproject;

public class Fixture {
    private long date;
    private String homeTeam;
    private String awayTeam;
    private int homeScore;
    private int awayScore;
    private String homeLogo;
    private String awayLogo;
    private String venue;
    private String matchstatus;

    public Fixture(long date, String homeTeam, String awayTeam, int homeScore, int awayScore, String homeLogo, String awayLogo, String venue, String matchstatus){
        this.date = date;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
        this.homeLogo = homeLogo;
        this.awayLogo = awayLogo;
        this.venue = venue;
        this.matchstatus = matchstatus;
    }

    public long getDate() {
        return date;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public int getHomeScore() {
        return homeScore;
    }

    public int getAwayScore() {
        return awayScore;
    }

    public String getHomeLogo() {
        return homeLogo;
    }

    public String getAwayLogo() {
        return awayLogo;
    }

    public String getVenue() {
        return venue;
    }

    public String getMatchstatus() {
        return matchstatus;
    }
}
