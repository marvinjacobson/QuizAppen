package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.quizapp.Common.Common;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Playing extends AppCompatActivity implements View.OnClickListener {

    final static long INTERVAL = 1000;
    final static long TIMEOUT = 30000;
    int progressValue = 0;
    CountDownTimer mCountDown;

    int index = 0, score = 0, thisQuestion = 0, totalQuestion, correctAnswer;

    FirebaseDatabase database;
    DatabaseReference questions;

    ProgressBar progressBar;
    Button btnA, btnB, btnC, btnD;
    TextView txtScore, txtQuestionNum, question_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing);

        database = FirebaseDatabase.getInstance();
        questions = database.getReference("Questions");

        txtScore = (TextView)findViewById(R.id.txtScore);
        txtQuestionNum = (TextView)findViewById(R.id.txtTotalQuestion);
        question_text = (TextView)findViewById(R.id.question_text);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        btnA = (Button)findViewById(R.id.btn_answerA);
        btnB = (Button)findViewById(R.id.btn_answerB);
        btnC = (Button)findViewById(R.id.btn_answerC);
        btnD = (Button)findViewById(R.id.btn_answerD);

        btnA.setOnClickListener(this);
        btnB.setOnClickListener(this);
        btnC.setOnClickListener(this);
        btnD.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        mCountDown.cancel();
        if(index < totalQuestion) {
            Button clickedButton = (Button)v;
            if(clickedButton.getText().equals(Common.questionList.get(index).getCorrectAnswer())) {
                score+=10;
                correctAnswer++;
                showQuestion(++index);
            }
            else {
                Intent intent = new Intent(this, Done.class);
                Bundle dataSend = new Bundle();
                dataSend.putInt("SCORE", score);
                dataSend.putInt("TOTAL", totalQuestion);
                dataSend.putInt("CORRECT", correctAnswer);
                intent.putExtras(dataSend);
                startActivity(intent);
                finish();
            }

            txtScore.setText(String.format("%d", score));
        }

    }

    private void showQuestion(int index) {


        if(index < totalQuestion) {
            thisQuestion++;
            txtQuestionNum.setText(String.format("%d / %d", thisQuestion, totalQuestion));
            progressBar.setProgress(0);
            progressValue = 0;

            question_text.setText(Common.questionList.get(index).getQuestion());

            btnA.setText(Common.questionList.get(index).getAnswerA());
            btnB.setText(Common.questionList.get(index).getAnswerB());
            btnC.setText(Common.questionList.get(index).getAnswerC());
            btnD.setText(Common.questionList.get(index).getAnswerD());


            mCountDown.start();

        }
        else {
            Intent intent = new Intent(this, Done.class);
            Bundle dataSend = new Bundle();
            dataSend.putInt("SCORE", score);
            dataSend.putInt("TOTAL", totalQuestion);
            dataSend.putInt("CORRECT", correctAnswer);
            intent.putExtras(dataSend);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        totalQuestion = Common.questionList.size();

        mCountDown = new CountDownTimer(TIMEOUT, INTERVAL) {
            @Override
            public void onTick(long minisec) {
                progressBar.setProgress(progressValue);
                progressValue++;
            }

            @Override
            public void onFinish() {
                mCountDown.cancel();
                showQuestion(++index);
            }
        };
        showQuestion(index);
    }
}