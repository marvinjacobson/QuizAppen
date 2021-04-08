package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Home extends AppCompatActivity {
    AppCompatButton createQuiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        createQuiz = (AppCompatButton) findViewById(R.id.createQuiz);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        createQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCreateQuiz();
            }
        });
    }

    public void openCreateQuiz(){
        Intent intent = new Intent(this, CreateQuizActivity.class);
        startActivity(intent);
    }
}