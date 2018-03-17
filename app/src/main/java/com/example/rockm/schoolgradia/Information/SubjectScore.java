package com.example.rockm.schoolgradia.Information;

import java.util.ArrayList;

/**
 * Created by rockm on 3/17/2018.
 */

public class SubjectScore extends Score {
    private int count;
    private ArrayList<IndividualScore> arrayListOfScores;
    public SubjectScore(String name){
        count=0;
        arrayListOfScores= new ArrayList<IndividualScore>();
        super.setName(name);
        super.setScore(0);
    }
    public void addGrade(IndividualScore score){
        arrayListOfScores.add(score);
        int totalGrade=0;
        for(IndividualScore s: arrayListOfScores)
            totalGrade+=s.getScore();
        setScore(totalGrade/++count);
    }
    public IndividualScore getIndvidualScore(int i){
        return arrayListOfScores.get(i);
    }
    public IndividualScore getIndvidualScore(IndividualScore score){
        IndividualScore result=null;

        for(IndividualScore s: arrayListOfScores){
            if(s.getScore()==score.getScore()&&s.getName()==score.getName()) {
                result = s;
                break;
            }
        }

        return result;
    }
}
