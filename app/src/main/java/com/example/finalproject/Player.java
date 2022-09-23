package com.example.finalproject;

public class Player {

    private String name;
    private String position;
    private String nationality;
    private int age;
    private int playerID;

    public Player(String name, String position, String nationality, int age, int playerID){
        this.name = name;
        this.position = position;
        this.nationality = nationality;
        this.age = age;
        this.playerID = playerID;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public String getNationality() {
        return nationality;
    }

    public int getAge() {
        return age;
    }

    public int getPlayerID() {
        return playerID;
    }
}
