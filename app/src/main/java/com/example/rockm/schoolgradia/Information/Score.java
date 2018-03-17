package com.example.rockm.schoolgradia.Information;

/**
 * Created by rockm on 3/4/2018.
 */

public abstract class Score {
    public Score(){

    }
    private int score;
    public void setScore(int scoreToSet){
        score=scoreToSet;
    }
    public int getScore(){
        return score;
    }
}
