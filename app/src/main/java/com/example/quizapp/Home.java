package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Home extends AppCompatActivity {

    private Button btnSettings, btn_createQuiz, btnCategories, btnFriends, btn_fave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btnSettings = (Button)findViewById(R.id.btn_gotoSettings);
        btnFriends = (Button)findViewById(R.id.btn_gotofriends);
        btn_createQuiz = (Button)findViewById(R.id.btn_createQuiz);
        btnCategories = (Button)findViewById(R.id.btn_categories);
        btn_fave = (Button)findViewById(R.id.btn_fave);

        //Öppnar Settings
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSettings();
            }
        });
        //Öppnar Settings
        btnFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFriends();
            }
        });
        //Öppnar CreateQuiz
        btn_createQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCreateQuiz();
            }
        });
        //Öppnar Kategorier
        btnCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCategories();
            }
        });
        //Öppnar PlayQuiz
        btn_fave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPlayQuiz();
            }
        });


    }

    public void openSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
    public void openFriends() {
        Intent intent = new Intent(this, AddFriendActivity.class);
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
    public void openPlayQuiz(){
        Intent intent = new Intent(this, PlayQuiz.class);
        intent.putExtra("quizID", "PlayQuizTestOXtpYobZJfVO99CQz1NdgHhcJCB3");
        startActivity(intent);
    }
}