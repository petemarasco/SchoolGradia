package com.example.rockm.schoolgradia.Display;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rockm.schoolgradia.Information.DBHelper;
import com.example.rockm.schoolgradia.Information.Score;
import com.example.rockm.schoolgradia.Information.SubjectScore;
import com.example.rockm.schoolgradia.R;

import java.util.ArrayList;

/**
 * Created by rockm on 3/4/2018.
 */


public class SubjectFragment extends Fragment {
    private SubjectScore subScore;
    private DBHelper DB;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        subScore= new SubjectScore("Example");



        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.mainscreen, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        Button addScoreButton= getActivity().findViewById(R.id.AddScore);
        addScoreButton.setOnClickListener(addGradeListener());
        Button setGradeButton = getActivity().findViewById(R.id.Confirm);
        setGradeButton.setOnClickListener(confirmGradeListener());
        Button resetButton = getActivity().findViewById(R.id.Reset);
        resetButton.setOnClickListener(resetListener());
        displayPage();
        Context thisContext = this.getContext();
        DB = new DBHelper(thisContext);
    }

    private View.OnClickListener addGradeListener(){
        return new View.OnClickListener() {
            public void onClick(View view) {
                    View popUp = getActivity().findViewById(R.id.popUp);
                    popUp.setVisibility(View.VISIBLE);
                    TextView scoreText= getActivity().findViewById(R.id.textGradeScore);
                    scoreText.setVisibility(View.INVISIBLE);
                LinearLayout layout = getActivity().findViewById(R.id.layoutScoreList);
                layout.setVisibility(View.INVISIBLE);
                }
        };
    }

    private View.OnClickListener confirmGradeListener(){
        return new View.OnClickListener() {
            public void onClick(View view) {
                //Initialize the Views of the two fields of user input
                TextView scoreToSetTextView = getActivity().findViewById(R.id.textIndividualScore);
                TextView scoreNameTextView = getActivity().findViewById(R.id.textScoreName);
                try{
                    //Get the two user inputs
                    Integer score=Integer.parseInt(scoreToSetTextView.getText().toString());
                    String name = scoreNameTextView.getText().toString();
                    //If no name or name Already taken, throw null Pointer Exception
                    if (name.isEmpty())
                        throw new NullPointerException();

                    for(Score s: DB.getGrades())
                        if(s.getName().equals(name))
                            throw new NullPointerException();
                    DB.insertGrade("Butts","Butts",name,score);
                }
                //If invalid score, throw this Exception
                catch (NumberFormatException e){
                    Context context = getActivity().getApplicationContext();
                    CharSequence text="Invalid score";
                    int duration=Toast.LENGTH_SHORT;
                    Toast toast=Toast.makeText(context,text,duration);
                    toast.show();
                }
                //If invalid name, throw this Exception
                catch (NullPointerException e){
                    Context context = getActivity().getApplicationContext();
                    CharSequence text="Invalid Name";
                    int duration=Toast.LENGTH_SHORT;
                    Toast toast=Toast.makeText(context,text,duration);
                    toast.show();
                }
            displayPage();
            }
        };
    }
    //Display Grades
    private void displayGrades(){
        //Initialize Views
        View popUp = getActivity().findViewById(R.id.popUp);
        LinearLayout layout = getActivity().findViewById(R.id.layoutScoreList);
        Button standard = getActivity().findViewById(R.id.Standard);
        //Reset Layout
        layout.removeAllViews();

        //If there some scores
        if(DB.numberOfIndividuals()!=0){
            ArrayList<Score> scores=DB.getGrades();

            for(Score s : scores){
                TextView individualScoreTextView= new TextView(getActivity());
                individualScoreTextView.setText(""+s.getName()+": "+s.getScore());
                individualScoreTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                layout.addView(individualScoreTextView);
                Button deleteGradeButton = new Button(getActivity());
                deleteGradeButton.setLayoutParams(standard.getLayoutParams());
                deleteGradeButton.setText("Delete");
                deleteGradeButton.setVisibility(View.VISIBLE);
                deleteGradeButton.setOnClickListener(gradeDelete(s.getName()));
                layout.addView(deleteGradeButton);
            }
            popUp.setVisibility(View.INVISIBLE);
            layout.setVisibility(View.VISIBLE);
        }

    }
    //Display page
    private void displayPage(){
        //Display Individual Grades
        displayGrades();
        //Display the overall Grade
        TextView scoreTextView= getActivity().findViewById(R.id.textGradeScore);
        scoreTextView.setText("Grade: "+DB.getTotalGrade()+"%");
        scoreTextView.setVisibility(View.VISIBLE);
    }
    private  View.OnClickListener gradeDelete(final String nameIndiv){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DB.deleteIndividual(nameIndiv);
                displayPage();
            }
        };
    }
    private  View.OnClickListener resetListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DB.resetDB();
                displayPage();

            }
        };
    }
}
