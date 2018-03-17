package com.example.rockm.schoolgradia.Display;




import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.rockm.schoolgradia.Display.MainScreen;
import com.example.rockm.schoolgradia.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager manager= getSupportFragmentManager();
        MainScreen mainFrag= new MainScreen();
        FragmentTransaction transaction= manager.beginTransaction();
        transaction.add(R.id.layout,mainFrag);
        transaction.commit();
    }
}
