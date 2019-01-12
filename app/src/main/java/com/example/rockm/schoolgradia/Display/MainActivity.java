package com.example.rockm.schoolgradia.Display;




import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.rockm.schoolgradia.R;


public class MainActivity extends AppCompatActivity {
    protected SQLiteDatabase subjectDataBase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        subjectDataBase= openOrCreateDatabase("DBName.db",MODE_PRIVATE,null);
        setContentView(R.layout.activity_main);
        FragmentManager manager= getSupportFragmentManager();
        SubjectFragment subjectFragment= new SubjectFragment();
        FragmentTransaction transaction= manager.beginTransaction();
        transaction.add(R.id.layout,subjectFragment);
        transaction.commit();
    }
}
