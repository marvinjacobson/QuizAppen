package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quizapp.Common.Common;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Home extends AppCompatActivity {

    DatabaseReference mDatabase;

    private Button btnSettings, btn_createQuiz, btnCategories, btnFriends, btn_fave, btn_day;

    private String favCat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        btnSettings = (Button) findViewById(R.id.btn_gotoSettings);
        btnFriends = (Button) findViewById(R.id.btn_gotofriends);
        btn_createQuiz = (Button) findViewById(R.id.btn_createQuiz);
        btnCategories = (Button) findViewById(R.id.btn_categories);
        btn_fave = (Button) findViewById(R.id.btn_fave);
        btn_day = (Button)findViewById(R.id.btn_quizOfDay);
        getFav();

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

        btn_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPlay();
                Common.categoryId = "07";
            }
        });


    }

    public void getFav(){
        String currentUser = FirebaseAuth.getInstance().getUid();
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("Users").child(currentUser).child("favCategory");
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               favCat = snapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void openPlay() {
        Intent intent = new Intent(this, Start.class);
        startActivity(intent);
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

    public void openPlayQuiz() {
        Intent intent = new Intent(this, PlayQuiz.class);
        intent.putExtra("quizID", "PlayQuizTestOXtpYobZJfVO99CQz1NdgHhcJCB3");
        startActivity(intent);
    }
}