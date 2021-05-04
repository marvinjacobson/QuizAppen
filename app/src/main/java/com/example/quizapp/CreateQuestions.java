package com.example.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.quizapp.Model.Question;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.quizapp.R.drawable.roundedet;
import static com.example.quizapp.R.drawable.roundedeterror;

public class CreateQuestions extends AppCompatActivity {
    AppCompatButton btn_nextQuestion;
    EditText edt_questioname, edt_option1, edt_option2, edt_option3;
    RadioButton rad_1, rad_2, rad_3;
    FirebaseDatabase database;
    private FirebaseAuth auth;
    DatabaseReference questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_create_questions);
        btn_nextQuestion = (AppCompatButton)findViewById(R.id.btn_nextQuestion);
        edt_option1 = (EditText)findViewById(R.id.edt_option1);
        edt_option2 = (EditText)findViewById(R.id.edt_option2);
        edt_option3 = (EditText)findViewById(R.id.edt_option3);
        rad_1 = (RadioButton)findViewById(R.id.rad_1);
        rad_2 = (RadioButton)findViewById(R.id.rad_2);
        rad_3 = (RadioButton)findViewById(R.id.rad_3);
        edt_questioname = (EditText)findViewById(R.id.edt_questionname);
        database = FirebaseDatabase.getInstance();
        questions = database.getReference("Question");


        btn_nextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                edt_questioname.setBackgroundResource(roundedet);
                edt_option1.setBackgroundResource(roundedet);
                edt_option2.setBackgroundResource(roundedet);
                edt_option3.setBackgroundResource(roundedet);


                String question = edt_questioname.getText().toString();
                Integer selected_answer = 0;

                String answer1 = edt_option1.getText().toString();
                String answer2 = edt_option2.getText().toString();
                String answer3 = edt_option3.getText().toString();

                if (rad_1.isChecked()) {
                    selected_answer = 1;
                }
                if (rad_2.isChecked()) {
                    selected_answer = 2;
                }
                if (rad_3.isChecked()) {
                    selected_answer = 3;
                }


                if (!(selected_answer == 0) || !TextUtils.isEmpty(question) || !TextUtils.isEmpty(answer1) || !TextUtils.isEmpty(answer2) || !TextUtils.isEmpty(answer3)) {

                    String quizID = intent.getStringExtra("quizID");
                    Integer questioncount = intent.getIntExtra("questionCount", 0);
                    Integer currentquestion = intent.getIntExtra("currentQuestion", 0);

                    createQuestion(question, answer1, answer2, answer3, selected_answer, questioncount, currentquestion, quizID);

                }
                if (TextUtils.isEmpty(question)) {

                    edt_questioname.setBackgroundResource(roundedeterror);
                    Toast.makeText(CreateQuestions.this, "Glöm inte att skriva din fråga", Toast.LENGTH_SHORT).show();

                }
                if (TextUtils.isEmpty(answer1)) {

                    edt_option1.setBackgroundResource(roundedeterror);
                    Toast.makeText(CreateQuestions.this, "Glöm inte att fylla i alla alternativ", Toast.LENGTH_SHORT).show();

                }
                if (TextUtils.isEmpty(answer2)) {

                    edt_option2.setBackgroundResource(roundedeterror);
                    Toast.makeText(CreateQuestions.this, "Glöm inte att fylla i alla alternativ", Toast.LENGTH_SHORT).show();

                }
                if (TextUtils.isEmpty(answer3)) {

                    edt_option3.setBackgroundResource(roundedeterror);
                    Toast.makeText(CreateQuestions.this, "Glöm inte att fylla i alla alternativ", Toast.LENGTH_SHORT).show();

                }
                if (selected_answer == 0) {

                    Toast.makeText(CreateQuestions.this, "Glöm inte att välja vilket alternativ som är rätt", Toast.LENGTH_SHORT).show();

                }




            } });


    }

    public void createQuestion(String questionText, String answer1, String answer2, String answer3, Integer correct_answer, Integer questioncount, Integer currentquestion, String quizID){
        String questionID = quizID + currentquestion.toString();
        Question question = new Question(questionID, questionText,currentquestion, correct_answer,answer1,answer2,answer3,quizID);
        questions.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                questions.child(question.getQuestionID()).setValue(question);
                Toast.makeText(CreateQuestions.this, "Din fråga har sparats", Toast.LENGTH_SHORT).show();
                if (currentquestion < questioncount){
                    Integer nextQuestion = currentquestion +1;
                    Intent i= new Intent(CreateQuestions.this, CreateQuestions.class);
                    i.putExtra("questionCount", questioncount);
                    i.putExtra("currentQuestion", nextQuestion);
                    i.putExtra("quizID", quizID);
                    startActivity(i);
                    finish();
                }
                else{
                    Intent i= new Intent(CreateQuestions.this, ShareQuiz.class);
                    i.putExtra("quizId", quizID);
                    startActivity(i);
                    finish();

                }
                }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
