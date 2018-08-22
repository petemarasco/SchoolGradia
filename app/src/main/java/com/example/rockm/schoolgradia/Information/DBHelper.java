package com.example.rockm.schoolgradia.Information;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import static android.R.attr.id;
import static android.R.attr.name;
import static android.R.attr.version;

/**
 * Created by rockm on 3/20/2018.
 */

/**
 * This Database Handles all of the
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
    public void resetDB(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("drop table "+GRADES_TABLE_NAME);
        db.execSQL(
                "create table "+ GRADES_TABLE_NAME+
                        " ("+GRADES_COLUMN_INDIVIDUAL_NAME+" text UNIQUE, "+GRADES_COLUMN_CRITERIA_NAME+" text, "+GRADES_COLUMN_SUBJECT_NAME+" text, "+GRADES_COLUMN_GRADE+" integer)"
        );
    }
    public boolean updateGrade(String subjectName,String criteriaName,String individualName, Integer grade){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();

        db.update(GRADES_TABLE_NAME, contentValues, "name="+name,null);
        return true;
    }
    public void deleteSubject (String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(GRADES_TABLE_NAME,
                GRADES_COLUMN_SUBJECT_NAME+"="+name,
                null);
    }
    public boolean insertGrade(String subjectName,String criteriaName,String individualName, Integer grade){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(GRADES_COLUMN_INDIVIDUAL_NAME,individualName);
        contentValues.put(GRADES_COLUMN_CRITERIA_NAME,criteriaName);
        contentValues.put(GRADES_COLUMN_SUBJECT_NAME,subjectName);
        contentValues.put(GRADES_COLUMN_GRADE,grade);
        db.insert(GRADES_TABLE_NAME,null,contentValues);
        return true;
    }
    public int numberOfindividuals(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, GRADES_TABLE_NAME);
        return numRows;
    }
    public Integer deleteIndividual (String name) {
        SQLiteDatabase db = this.getWritableDatabase();
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
    public Integer getTotalGrade(){
        Integer result=0;
        int count=0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor theCursor =  db.rawQuery( "select "+ GRADES_COLUMN_GRADE +" from "+GRADES_TABLE_NAME, null );
        theCursor.moveToFirst();
        do{
            result+=theCursor.getInt(theCursor.getColumnIndex(GRADES_COLUMN_GRADE));
            count++;
        }while (theCursor.moveToNext());
        result/=count;
        return result;
    }

    public ArrayList<Score> getGrades(){
        ArrayList<Score> scores = new ArrayList<Score>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor theCursorIndivScores =  db.rawQuery( "select "+ GRADES_COLUMN_GRADE +" from "+GRADES_TABLE_NAME, null );
        Cursor theCursorNames =  db.rawQuery( "select "+ GRADES_COLUMN_INDIVIDUAL_NAME +" from "+GRADES_TABLE_NAME, null );
        theCursorIndivScores.moveToFirst();
        theCursorNames.moveToFirst();
        Score temp;

        do{
            int score=theCursorIndivScores.getInt(theCursorIndivScores.getColumnIndex(GRADES_COLUMN_GRADE));
            String name =theCursorNames.getString(theCursorNames.getColumnIndex(GRADES_COLUMN_INDIVIDUAL_NAME));
            temp= new IndividualScore(score,name);
            scores.add(temp);

        }while (theCursorIndivScores.moveToNext()&&theCursorNames.moveToNext());


        return scores;
    }

}
