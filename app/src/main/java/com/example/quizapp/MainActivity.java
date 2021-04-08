package com.example.quizapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.quizapp.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {

    EditText edtUser;
    EditText edtPassword;
    Button btnSignUp, btnLogIn;
    boolean logStatus = false;
    FirebaseDatabase database;
    DatabaseReference users;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");

        edtUser = (EditText)findViewById(R.id.edtUser);
        edtPassword = (EditText)findViewById(R.id.edtPassword);

        btnLogIn = (Button)findViewById(R.id.btn_log_in);
        btnSignUp = (Button)findViewById(R.id.btn_sign_up);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivity();
            }
        });

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logIn(edtUser.getText().toString(), edtPassword.getText().toString());
            }
        });
    }
    public void openNewActivity(){
        Intent intent = new Intent(this, RegActivity.class);
        startActivity(intent);
    }

    public void openHomeActivity() {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

    //Kollar så man skriver in rätt saker i rätt fält samt loggar in
    private void logIn(String user, String pwd) {
        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    if(dataSnapshot.child(user).exists()) {
                        if(!user.isEmpty()) {
                            User login = dataSnapshot.child(user).getValue(User.class);
                            if(login.getPassword().equals(pwd)){
                                Toast.makeText(MainActivity.this, "Login ok", Toast.LENGTH_SHORT).show();
                                System.out.println("LOG IN OK!!!!!!!");
                                openHomeActivity();
                            }
                            else
                                Toast.makeText(MainActivity.this, "Not ok", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(MainActivity.this, "Enter user name", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                        Toast.makeText(MainActivity.this, "User does not exists", Toast.LENGTH_SHORT).show();
                }catch (Exception e) {
                    System.out.println(e);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}