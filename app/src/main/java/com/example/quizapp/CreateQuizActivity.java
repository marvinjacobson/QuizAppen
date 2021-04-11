
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
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        count.setAdapter(arrayAdapter2);


    }
}