package com.example.accelerometerprototypegame;

public class Highscore <Highscore>{
    int _id;
    String _name;
    int _highscore;

    public Highscore(){   }
    public Highscore(int id, String name, int highscore){
        this._id = id;
        this._name = name;
        this._highscore = highscore;
    }

    public Highscore(String name, int highscore){
        this._name = name;
        this._highscore = highscore;
    }
    public int getID(){
        return this._id;
    }

    public void setID(int id){
        this._id = id;
    }

    public String getName(){
        return this._name;
    }

    public void setName(String name){
        this._name = name;
    }

    public int getHighscore(){
        return this._highscore;
    }

    public void setHighscore(int highscore){
        this._highscore = highscore;
    }}
