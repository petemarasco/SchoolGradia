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


public class MainScreen extends Fragment {
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

       TextView scoreText= getActivity().findViewById(R.id.textGradeScore);
        scoreText.setText("Grade: "+subScore.getScore()+"%");
        super.onViewCreated(view, savedInstanceState);
        Button popPopUpButton= getActivity().findViewById(R.id.buttonPopPopUp);
        popPopUpButton.setOnClickListener(popUpListener());
        Button setGrade = getActivity().findViewById(R.id.Confirm);
        setGrade.setOnClickListener(gradeAddListener());
        Button reset = getActivity().findViewById(R.id.Reset);
        reset.setOnClickListener(resetListener());
        Context thisContext = this.getContext();
        DB = new DBHelper(thisContext);
    }

    private View.OnClickListener popUpListener(){
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

    private View.OnClickListener gradeAddListener(){
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
                        if(s.getName()==name)
                            throw new NullPointerException();
                    DB.insertGrade("Butts","Butts",name,score.intValue());
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
            displayGrades();
            }
        };
    }

    private void displayGrades(){
        //Initialize Views
        View popUp = getActivity().findViewById(R.id.popUp);
        TextView scoreTextView= getActivity().findViewById(R.id.textGradeScore);
        LinearLayout layout = getActivity().findViewById(R.id.layoutScoreList);
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
                deleteGradeButton.setText("Delete");
                deleteGradeButton.setOnClickListener(gradeDelete(s.getName()));
                layout.addView(deleteGradeButton);
            }
            scoreTextView.setVisibility(View.VISIBLE);
            popUp.setVisibility(View.INVISIBLE);
            layout.setVisibility(View.VISIBLE);
        }
        //set the grade
        scoreTextView.setText("Grade: "+DB.getTotalGrade()+"%");



    }
    private  View.OnClickListener gradeDelete(final String nameIndiv){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DB.deleteIndividual(nameIndiv);
                displayGrades();
            }
        };
    }
    private  View.OnClickListener resetListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DB.resetDB();
                displayGrades();

            }
        };
    }
}
