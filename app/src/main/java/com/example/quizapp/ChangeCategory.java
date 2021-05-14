package com.example.quizapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChangeCategory extends AppCompatActivity {
    Button btn_saveNewCategory;
    DatabaseReference categories;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_category);
        btn_saveNewCategory = findViewById(R.id.btn_SaveNewCategory);
        List<String> catNames;
        Spinner newCategory = findViewById(R.id.spinner_new_fav_cat);
        catNames = new ArrayList<>();
        categories = FirebaseDatabase.getInstance().getReference();
        categories.child("Category").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Hämtar alla kategorier och visar dom i vår spinner (Dropdown menu)
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    String spinnerName = childSnapshot.child("Name").getValue(String.class);
                    catNames.add(spinnerName);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(ChangeCategory.this, android.R.layout.simple_spinner_item, catNames);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                newCategory.setAdapter(arrayAdapter);
                //Ändrar favorit kategori när du klickar på spara knappen
                btn_saveNewCategory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String uId = FirebaseAuth.getInstance().getUid();
                        System.out.println(uId);
                        String newFavCat = newCategory.getSelectedItem().toString();
                        categories.child("Users").child(uId).child("favCategory").setValue(newFavCat);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
