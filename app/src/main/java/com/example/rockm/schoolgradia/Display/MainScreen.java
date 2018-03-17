package com.example.rockm.schoolgradia.Display;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.rockm.schoolgradia.Information.IndividualScore;
import com.example.rockm.schoolgradia.Information.Score;
import com.example.rockm.schoolgradia.R;

/**
 * Created by rockm on 3/4/2018.
 */


public class MainScreen extends Fragment {
    private IndividualScore score;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        score= new IndividualScore(0,"Example");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.mainscreen, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        score.changeGrade(100);
       TextView scoreText= getActivity().findViewById(R.id.textScore);
        scoreText.setText(score.getScore()+"");
        super.onViewCreated(view, savedInstanceState);
        Button popPopUpButton= getActivity().findViewById(R.id.buttonPopPopUp);
        popPopUpButton.setOnClickListener(popUpListener());
        Button setGrade = getActivity().findViewById(R.id.Confirm);
        setGrade.setOnClickListener(gradeChangeListener());

    }

    private View.OnClickListener popUpListener(){
        return new View.OnClickListener() {
            public void onClick(View view) {
                    View popUp = getActivity().findViewById(R.id.popUp);
                    popUp.setVisibility(View.VISIBLE);
                    TextView scoreText= getActivity().findViewById(R.id.textScore);
                    scoreText.setVisibility(View.INVISIBLE);
                }
        };
    }

    private View.OnClickListener gradeChangeListener(){
        return new View.OnClickListener() {
            public void onClick(View view) {
                View popUp = getActivity().findViewById(R.id.popUp);
                TextView scoreToSetText = getActivity().findViewById(R.id.textScoreToBeSet);
                score.changeGrade(Integer.parseInt(scoreToSetText.getText().toString()));
                TextView scoreText= getActivity().findViewById(R.id.textScore);
                scoreText.setText(score.getScore()+"");
                scoreText.setVisibility(View.VISIBLE);
                popUp.setVisibility(View.INVISIBLE);
            }
        };
    }
}
