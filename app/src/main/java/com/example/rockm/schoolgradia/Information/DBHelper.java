package com.example.rockm.schoolgradia.Information;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.R.attr.id;
import static android.R.attr.version;

/**
 * Created by rockm on 3/20/2018.
 */

/**
 * This Database Handles all of the
 */
public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "SubjectDBName.db";
    public static final String SUBJECTS_TABLE_NAME = "subjects";
    public static final String SUBJECTS_COLUMN_NAME = "name";
    public static final String SUBJECTS_COLUMN_GRADE = "grade";
    public static final String INDIVIDUALS_TABLE_NAME = "individual";
    public static final String INDIVIDUALS_COLUMN_NAME = "name";
    public static final String INDIVIDUALS_COLUMN_GRADE = "grade";
    public static final String INDIVIDUALS_COLUMN_SUBJECT_NAME = "subject_name";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME,null,1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table subjects "+ "(name text primary key, grade integer)");
        db.execSQL("create table individual "+ "(name text primary key, grade integer, subject_name text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(db);
    }

    public boolean insertSubject(String name, Integer grade){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(SUBJECTS_COLUMN_NAME,name);
        contentValues.put(SUBJECTS_COLUMN_GRADE,grade);
        db.insert(SUBJECTS_TABLE_NAME,null,contentValues);
        return true;
    }

    public int numberOfSubjects(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, SUBJECTS_TABLE_NAME);
        return numRows;
    }

    public boolean updateSubject(String name, Integer grade){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(SUBJECTS_COLUMN_NAME,name);
        contentValues.put(SUBJECTS_COLUMN_GRADE,grade);
        db.update(SUBJECTS_TABLE_NAME,contentValues,"name ="+name, null);
        return true;
    }
    public Integer deleteSubject (String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("individuals",
                "subject_name ="+name,
                null);
        return db.delete("subjects",
                "name ="+name,
                null);
    }
    public boolean insertIndividual(String name, Integer grade, String subject_name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(INDIVIDUALS_COLUMN_NAME,name);
        contentValues.put( INDIVIDUALS_COLUMN_GRADE,grade);
        contentValues.put(INDIVIDUALS_COLUMN_SUBJECT_NAME,subject_name);
        db.insert(INDIVIDUALS_TABLE_NAME,null,contentValues);
        return true;
    }
    public int numberOfindividuals(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, INDIVIDUALS_TABLE_NAME);
        return numRows;
    }
    public boolean updateIndividual(String name, Integer grade, String subject_name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(INDIVIDUALS_COLUMN_NAME,name);
        contentValues.put( INDIVIDUALS_COLUMN_GRADE,grade);
        contentValues.put(INDIVIDUALS_COLUMN_SUBJECT_NAME,subject_name);
        db.update(INDIVIDUALS_TABLE_NAME,contentValues,"name ="+name, null);
        return true;
    }
    public Integer deleteIndividual (String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("individuals",
                "name ="+name,
                null);
    }

}
