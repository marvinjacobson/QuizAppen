
package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class CreateQuizActivity extends AppCompatActivity {
    Spinner spinner_categori, spinner_number;
    EditText edtQuizName;
    String[] Categories=new  String[] {"Film", "Natur", "Musik"};
    Integer[] QuizCount = {5, 6, 7, 9, 10};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_quiz);

        edtQuizName = (EditText)findViewById(R.id.edtQuizName);
        Spinner category = (Spinner)findViewById(R.id.spinner_categori);
        Spinner count = (Spinner)findViewById(R.id.spinner_number);


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Categories);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(arrayAdapter);

        ArrayAdapter<Integer> arrayAdapter2 = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, QuizCount);
<<<<<<< Updated upstream
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        count.setAdapter(arrayAdapter);
=======
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        count.setAdapter(arrayAdapter2);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String questionCount = count.getSelectedItem().toString();
                Integer questCount_ = Integer.parseInt(questionCount);
                String selectedCat = category.getSelectedItem().toString();
                String questionName = edtQuizName.getText().toString();

                if (TextUtils.isEmpty(questionName)){
                    edtQuizName.setBackgroundResource(roundedeterror);
                    Toast.makeText(CreateQuizActivity.this, "Ge din quiz ett namn", Toast.LENGTH_SHORT).show();
                }
                if (questionName.length() > 15){
                    edtQuizName.setBackgroundResource(roundedeterror);
                    Toast.makeText(CreateQuizActivity.this, "Ditt namn får innehålla max 15 tecken", Toast.LENGTH_SHORT).show();
                }
                if(!TextUtils.isEmpty(questionName) || questionName.length() > 15){

                    createNewQuiz(questCount_, selectedCat, questionName);
                }



            }
        });


    }
    public void createNewQuiz(Integer count, String cat, String name){

        String quizID = name + currentUser;
        quizID = quizID.replaceAll("\\s+","");
        Quiz quiz = new Quiz(name,currentUser,cat, count, quizID);
        String finalQuizID = quizID;
        quizes.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.child(quiz.getQuizID()).exists()){
                    edtQuizName.setBackgroundResource(roundedeterror);
                    Toast.makeText(CreateQuizActivity.this, "Du har redan skapat en quiz med det här namnet", Toast.LENGTH_SHORT).show();

                }
                else{
                    Integer currentquestion = 1;
                    quizes.child(quiz.getQuizID()).setValue(quiz);
                    Toast.makeText(CreateQuizActivity.this, "Din quiz har skapats", Toast.LENGTH_SHORT).show();
                    Intent i= new Intent(CreateQuizActivity.this, CreateQuestions.class);
                    i.putExtra("questionCount", count);
                    i.putExtra("currentQuestion", currentquestion);
                    i.putExtra("quizID", finalQuizID);
                    startActivity(i);
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
>>>>>>> Stashed changes


    }
}