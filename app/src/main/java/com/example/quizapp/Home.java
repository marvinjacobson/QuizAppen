package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Home extends AppCompatActivity {

<<<<<<< Updated upstream
    private Button btnSettings;
=======
    private Button btnSettings, btn_createQuiz, btnCategories, btnFriends, btnFave;
>>>>>>> Stashed changes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
<<<<<<< Updated upstream
        btnSettings = (Button)findViewById(R.id.gotoSettings);
=======
        btnSettings = (Button)findViewById(R.id.btn_gotoSettings);
        btnFriends = (Button)findViewById(R.id.btn_gotofriends);
        btnFave = (AppCompatButton)findViewById(R.id.btn_fave);
        btn_createQuiz = (Button)findViewById(R.id.btn_A3);
        btnCategories = (Button)findViewById(R.id.btn_A2);
>>>>>>> Stashed changes

        //Öppnar Settings
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSettings();
            }
        });
<<<<<<< Updated upstream
=======
        btnFave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPlayQuiz();
            }
        });
        btnFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFriends();
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

>>>>>>> Stashed changes
    }
    public void openPlayQuiz(){
        Intent intent = new Intent(this, PlayQuiz.class);
        intent.putExtra("quizID", "PlayQuizTestOXtpYobZJfVO99CQz1NdgHhcJCB3");
        startActivity(intent);
    }

    public void openSettings() {
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }
}