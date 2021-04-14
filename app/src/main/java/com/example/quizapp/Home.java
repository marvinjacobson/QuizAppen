package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.quizapp.Model.Categories;

public class Home extends AppCompatActivity {

    private Button btnSettings, btn_createQuiz, btnCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btnSettings = (Button)findViewById(R.id.btn_gotoSettings);
        btn_createQuiz = (Button)findViewById(R.id.btn_createQuiz);
        btnCategories = (Button)findViewById(R.id.btn_categories);

        //Öppnar Settings
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSettings();
            }
        });
        btn_createQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCreateQuiz();
            }
        });

        btnCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCategories();
            }
        });

    }

    public void openSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
    public void openCreateQuiz() {
        Intent intent = new Intent(this, CreateQuizActivity.class);
        startActivity(intent);
    }

    public void openCategories() {
        Intent intent = new Intent(this, Categories.class);
        startActivity(intent);
    }
}