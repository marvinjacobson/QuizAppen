package com.example.quizapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizapp.Model.Question;
import com.example.quizapp.Model.Quiz;
import com.example.quizapp.Model.QuizResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Result;

import static com.example.quizapp.R.drawable.roundedeterror;
import static com.example.quizapp.R.drawable.roundedetsuccess;

public class PlayQuiz extends AppCompatActivity {
    private TextView tv_Question;
    private AppCompatButton btn_A1, btn_A2, btn_A3;
    FirebaseDatabase database;
    private DatabaseReference mQuizDatabase;
    private FirebaseAuth auth;
    private  String question;
    private String firstquestion;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance();
        setContentView(R.layout.activity_play_quiz);
        auth = FirebaseAuth.getInstance();
        mQuizDatabase = FirebaseDatabase.getInstance().getReference("Question");
        Intent intent = getIntent();
        Integer score = intent.getIntExtra("currentScore", 0);
        firstquestion = intent.getStringExtra("quizID");
        String currentQuestion = intent.getStringExtra("currentQuestion");
        if(firstquestion != null){
            question = firstquestion + 1;
        }
        else if(currentQuestion != null){
            question = currentQuestion;
        }



        tv_Question = (TextView)findViewById(R.id.tv_Question);
        btn_A1 = (AppCompatButton)findViewById(R.id.btn_A1);
        btn_A2 = (AppCompatButton)findViewById(R.id.btn_A2);
        btn_A3 = (AppCompatButton)findViewById(R.id.btn_A3);


        ArrayList<Question> questionArray = new ArrayList<Question>();


        mQuizDatabase.orderByChild("questionID").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot postSnapsshot: snapshot.getChildren()){
                    if(postSnapsshot.getKey() == question){
                        Question ques = postSnapsshot.getValue(Question.class);

                        questionArray.add(ques);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        Question currentques = questionArray.get(0);







        String A1 = currentques.getAnswer1();
        String A2 = currentques.getAnswer2();
        String A3 = currentques.getAnswer3();
        tv_Question.setText("test");
        btn_A1.setText(A2);
        btn_A2.setText(A2);
        btn_A3.setText(A3);
        Integer corrAns = currentques.getCorrectanswer();
        final Integer correctanswer = corrAns;
        final String nextQuestion = currentques.getNextQuestion();
        Integer answer = 0;

        btn_A1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer answer = 1;
                if(answer == correctanswer) {
                    btn_A1.setBackgroundResource(roundedetsuccess);
                }
                else{
                    btn_A1.setBackgroundResource(roundedeterror);
                }
                QuestionAnswered(nextQuestion, answer, correctanswer, score);

            }
        });
        btn_A2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer answer = 2;
                if(answer == correctanswer) {
                    btn_A2.setBackgroundResource(roundedetsuccess);
                }
                else{
                    btn_A2.setBackgroundResource(roundedeterror);
                }
                QuestionAnswered(nextQuestion, answer, correctanswer, score);

            }
        });

        btn_A3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer answer = 3;
                if(answer == correctanswer) {
                    btn_A3.setBackgroundResource(roundedetsuccess);
                }
                else{
                    btn_A3.setBackgroundResource(roundedeterror);
                }
                QuestionAnswered(nextQuestion, answer, correctanswer, score);

            }
        });




    }


    public void QuestionAnswered(String nextQuestion, Integer answer, Integer correctAnswer, Integer score){
        String currentplayer = auth.getUid();
        String resultID = currentplayer + question;

        if (answer == correctAnswer){
            score ++;
        }

        if(nextQuestion.equals("lastquestion")){
            QuizResult result = new QuizResult(resultID, question, currentplayer, score);
            mQuizDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    mQuizDatabase.child(result.getResultID()).setValue(result);
                    Toast.makeText(PlayQuiz.this, "Ditt resultat har registrerats", Toast.LENGTH_SHORT).show();
                    Intent i= new Intent(PlayQuiz.this, PlayQuiz.class);
                    startActivity(i);
                    finish();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        }
        else{
            Intent i= new Intent(PlayQuiz.this, PlayQuiz.class);
            i.putExtra("currentScore", score);
            i.putExtra("currentQuestion", nextQuestion);
            startActivity(i);
            finish();

        }



    }



}