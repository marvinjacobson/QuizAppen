
package com.example.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.quizapp.Model.Quiz;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.quizapp.R.drawable.roundedeterror;

public class CreateQuizActivity extends AppCompatActivity {
    Spinner spinner_categori, spinner_number;
    AppCompatButton btn_next;
    EditText edtQuizName;
    DatabaseReference quizes, categories;

    List<String> catNames;
    /*String[] Categories=new  String[] {"Film", "Natur", "Musik"};*/
    Integer[] QuizCount = {5, 6, 7, 9, 10};
    String currentUser;
    FirebaseDatabase database;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_quiz);
        btn_next = (AppCompatButton)findViewById(R.id.btn_Next);
        edtQuizName = (EditText)findViewById(R.id.edtQuizName);
        Spinner category = (Spinner)findViewById(R.id.spinner_categori);
        Spinner count = (Spinner)findViewById(R.id.spinner_number);
        auth = FirebaseAuth.getInstance();
        currentUser = FirebaseAuth.getInstance().getUid();
        database = FirebaseDatabase.getInstance();
        quizes = database.getReference("Quiz");

        catNames = new ArrayList<>();
        categories = FirebaseDatabase.getInstance().getReference();
        categories.child("Category").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot childSnapshot : snapshot.getChildren()) {
                    String spinnerName = childSnapshot.child("Name").getValue(String.class);
                    catNames.add(spinnerName);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(CreateQuizActivity.this, android.R.layout.simple_spinner_item, catNames);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                category.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        /*ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Categories);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(arrayAdapter);*/

        ArrayAdapter<Integer> arrayAdapter2 = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, QuizCount);
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
        quizes.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean existing;
                existing = snapshot.child(quiz.getQuizID()).exists();
                System.out.println(existing);
                if(!existing){
                    edtQuizName.setBackgroundResource(roundedeterror);
                    Toast.makeText(CreateQuizActivity.this, "Du har redan skapat en quiz med det här namnet", Toast.LENGTH_SHORT).show();

                }
                else{

                    quizes.child(quiz.getQuizName()).setValue(quiz);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}