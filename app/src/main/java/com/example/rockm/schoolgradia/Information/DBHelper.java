package com.example.rockm.schoolgradia.Information;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import static android.R.attr.name;

/**
 * Created by rockm on 3/20/2018.
 */

/**
 * This Database Handles all of the Information
 * It resets,inserts,deletes, and gives size of DB
 */
public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "SubjectDBName.db";
    public static final String GRADES_TABLE_NAME = "gradesTable";
    public static final String GRADES_COLUMN_GRADE = "grade";
    public static final String GRADES_COLUMN_SUBJECT_NAME = "subject_name";
    public static final String GRADES_COLUMN_CRITERIA_NAME = "criteria_name";
    public static final String GRADES_COLUMN_INDIVIDUAL_NAME = "individual_name";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME,null,8);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " +GRADES_TABLE_NAME);
        db.execSQL(
                "create table "+ GRADES_TABLE_NAME+
                " ("+GRADES_COLUMN_INDIVIDUAL_NAME+" text UNIQUE, "+GRADES_COLUMN_CRITERIA_NAME+" text, "+GRADES_COLUMN_SUBJECT_NAME+" text, "+GRADES_COLUMN_GRADE+" integer)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " +GRADES_TABLE_NAME);
        onCreate(db);
    }

//    public boolean insertSubject(String name, Integer grade){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues= new ContentValues();
//        contentValues.put(SUBJECTS_COLUMN_NAME,name);
//        contentValues.put(SUBJECTS_COLUMN_GRADE,grade);
//        db.insert(SUBJECTS_TABLE_NAME,null,contentValues);
//        return true;
//    }

//    public int numberOfSubjects(){
//        SQLiteDatabase db = this.getReadableDatabase();
//        int numRows = (int) DatabaseUtils.queryNumEntries(db, SUBJECTS_TABLE_NAME);
//        return numRows;
//    }

    /**
     * Resets the Database
     */
    public void resetDB(){
        //Get DB
        SQLiteDatabase db = this.getWritableDatabase();
        //Delete Table
        db.execSQL("drop table "+GRADES_TABLE_NAME);
        //Create Table
        db.execSQL(
                "create table "+ GRADES_TABLE_NAME+
                        " ("+GRADES_COLUMN_INDIVIDUAL_NAME+" text UNIQUE, "+GRADES_COLUMN_CRITERIA_NAME+" text, "+GRADES_COLUMN_SUBJECT_NAME+" text, "+GRADES_COLUMN_GRADE+" integer)"
        );
    }

    /**
     * Update the Grade
     * @param subjectName
     * @param criteriaName
     * @param individualName
     * @param grade
     * @return success
     */

    public boolean updateGrade(String subjectName,String criteriaName,String individualName, Integer grade){
        //Gets the Datatbase
        SQLiteDatabase db = this.getWritableDatabase();
        //Object that holds the information about the grade
        ContentValues contentValues= new ContentValues();
        //Inserting the values
        contentValues.put(GRADES_COLUMN_INDIVIDUAL_NAME,individualName);
        contentValues.put(GRADES_COLUMN_CRITERIA_NAME,criteriaName);
        contentValues.put(GRADES_COLUMN_SUBJECT_NAME,subjectName);
        contentValues.put(GRADES_COLUMN_GRADE,grade);
        //Updating the Database
        db.update(GRADES_TABLE_NAME, contentValues, "name="+name,null);
        return true;
    }

    /**
     *  Removes the specified grade from the Database
     * @param name name of the grade to be removed
     */
    public void deleteSubject (String name) {
        //Gets the DB
        SQLiteDatabase db = this.getWritableDatabase();
        //Deletes the object
        db.delete(GRADES_TABLE_NAME,
                GRADES_COLUMN_SUBJECT_NAME+"="+name,
                null);
    }

    /**
     * Adds the Grade to the Database
     * @param subjectName
     * @param criteriaName
     * @param individualName
     * @param grade
     * @return That the operation worked
     */

    public boolean insertGrade(String subjectName,String criteriaName,String individualName, Integer grade){
        //Gets the Datatbase
        SQLiteDatabase db = this.getWritableDatabase();
        //Object that holds the information about the grade
        ContentValues contentValues= new ContentValues();
        //Inserting the values
        contentValues.put(GRADES_COLUMN_INDIVIDUAL_NAME,individualName);
        contentValues.put(GRADES_COLUMN_CRITERIA_NAME,criteriaName);
        contentValues.put(GRADES_COLUMN_SUBJECT_NAME,subjectName);
        contentValues.put(GRADES_COLUMN_GRADE,grade);
        //Updating the Database
        db.insert(GRADES_TABLE_NAME,null,contentValues);
        return true;
    }

    /**
     * Size of Grade
     * @return The number of Individuals
     */
    public int numberOfIndividuals(){
        //Gets the Database
        SQLiteDatabase db = this.getReadableDatabase();
        //get the number of rows, which equals the number of individuals
        int numRows = (int) DatabaseUtils.queryNumEntries(db, GRADES_TABLE_NAME);
        return numRows;
    }

    /**
     * Deletes the grade base off String, name, given
     * @param name
     * @return The grade deleted
     */
    public Integer deleteIndividual (String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        //Deletes the individual
        return db.delete(GRADES_TABLE_NAME,
                GRADES_COLUMN_INDIVIDUAL_NAME+"="+"'"+name+"'",
                null);
    }
    public void deleteCriteria (String name) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(GRADES_TABLE_NAME,
                GRADES_COLUMN_CRITERIA_NAME+"="+name,
                null);
    }

    /**
     * Gets the total grade of Student
     * @return the total
     */
    public Integer getTotalGrade() {
        Integer result = 0;
        int count = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        //An iterator that goes over the grades
        Cursor theCursor = db.rawQuery("select " + GRADES_COLUMN_GRADE + " from " + GRADES_TABLE_NAME, null);
        //Moves the iterator to the beginning
        theCursor.moveToFirst();
        //If the there is no grades, set it as 0
        if (numberOfIndividuals() < 1)
            return 0;
        //Sums up all the grades
        do {
            result += theCursor.getInt(theCursor.getColumnIndex(GRADES_COLUMN_GRADE));
            count++;
        } while (theCursor.moveToNext());
        result /= count;
        return result;
    }

    /**
     * @return an arrayList of the grades
     */
    public ArrayList<Score> getGrades(){
        ArrayList<Score> scores = new ArrayList<Score>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor theCursorIndivScores =  db.rawQuery( "select "+ GRADES_COLUMN_GRADE +" from "+GRADES_TABLE_NAME, null );
        Cursor theCursorNames =  db.rawQuery( "select "+ GRADES_COLUMN_INDIVIDUAL_NAME +" from "+GRADES_TABLE_NAME, null );
        theCursorIndivScores.moveToFirst();
        theCursorNames.moveToFirst();
        Score temp;
        if(numberOfIndividuals()>0) {
            do {
                int score = theCursorIndivScores.getInt(theCursorIndivScores.getColumnIndex(GRADES_COLUMN_GRADE));
                String name = theCursorNames.getString(theCursorNames.getColumnIndex(GRADES_COLUMN_INDIVIDUAL_NAME));
                temp = new IndividualScore(score, name);
                scores.add(temp);

            } while (theCursorIndivScores.moveToNext() && theCursorNames.moveToNext());
        }

        return scores;
    }

}
