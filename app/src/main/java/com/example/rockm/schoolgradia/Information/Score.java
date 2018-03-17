package com.example.rockm.schoolgradia.Information;

/**
 * Created by rockm on 3/4/2018.
 */

public abstract class Score {

    private String name;

    public String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }

    private int score;

    protected void setScore(int scoreToSet){
        score=scoreToSet;
    }
    public int getScore(){
        return score;
    }
}
