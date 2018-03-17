package com.example.rockm.schoolgradia.Information;

/**
 * Created by rockm on 3/4/2018.
 */

public class IndividualScore extends Score {

    public IndividualScore(int score,String name){
        setScore(score);
        setName(name);
    }
    public void changeGrade(int score){
        setScore(score);
    }
}
