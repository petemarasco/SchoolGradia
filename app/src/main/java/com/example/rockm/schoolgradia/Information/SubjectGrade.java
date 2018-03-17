package com.example.rockm.schoolgradia.Information;

/**
 * Created by rockm on 3/17/2018.
 */

public class SubjectGrade extends Score {
    private int count;
    private int totalGrade;
    public SubjectGrade(String name){
        count=0;
        totalGrade=0;
        super.setName(name);
        super.setScore(0);
    }
    public void addGrade(int grade){
        totalGrade+=grade;
        setScore(totalGrade/++count);
    }
}
