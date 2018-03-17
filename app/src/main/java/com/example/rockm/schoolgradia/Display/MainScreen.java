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

import com.example.rockm.schoolgradia.Information.IndividualScore;
import com.example.rockm.schoolgradia.Information.SubjectScore;
import com.example.rockm.schoolgradia.R;

import static android.R.attr.name;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by rockm on 3/4/2018.
 */


public class MainScreen extends Fragment {
    private SubjectScore subScore;
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

    }

    private View.OnClickListener popUpListener(){
        return new View.OnClickListener() {
            public void onClick(View view) {
                    View popUp = getActivity().findViewById(R.id.popUp);
                    popUp.setVisibility(View.VISIBLE);
                    TextView scoreText= getActivity().findViewById(R.id.textGradeScore);
                    scoreText.setVisibility(View.INVISIBLE);
                }
        };
    }

    private View.OnClickListener gradeAddListener(){
        return new View.OnClickListener() {
            public void onClick(View view) {
                View popUp = getActivity().findViewById(R.id.popUp);
                TextView scoreToSetTextView = getActivity().findViewById(R.id.textIndividualScore);
                TextView scoreTextView= getActivity().findViewById(R.id.textGradeScore);
                TextView scoreNameTextView = getActivity().findViewById(R.id.textScoreName);
                TextView individualScoreTextView= new TextView(getActivity());
                LinearLayout layout = getActivity().findViewById(R.id.layoutScoreList);
                layout.setVisibility(View.INVISIBLE);
                try{
                    Integer score=Integer.parseInt(scoreToSetTextView.getText().toString());
                    String name = scoreNameTextView.getText().toString();
                    if (name.isEmpty())
                        throw new NullPointerException();
                    IndividualScore individualScore= new IndividualScore(score,name);
                    subScore.addGrade(individualScore);
                    scoreTextView.setText("Grade: "+subScore.getScore()+"");
                    individualScoreTextView.setText("|"+individualScore.getName()+"|: "+individualScore.getScore());
                    individualScoreTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    layout.addView(individualScoreTextView);
                }
                catch (NumberFormatException e){
                    Context context = getActivity().getApplicationContext();
                    CharSequence text="Invalid score";
                    int duration=Toast.LENGTH_SHORT;
                    Toast toast=Toast.makeText(context,text,duration);
                    toast.show();
                }
                catch (NullPointerException e){
                    Context context = getActivity().getApplicationContext();
                    CharSequence text="Invalid Name";
                    int duration=Toast.LENGTH_SHORT;
                    Toast toast=Toast.makeText(context,text,duration);
                    toast.show();
                }
                scoreTextView.setVisibility(View.VISIBLE);
                popUp.setVisibility(View.INVISIBLE);
                layout.setVisibility(View.VISIBLE);
            }
        };
    }
}
