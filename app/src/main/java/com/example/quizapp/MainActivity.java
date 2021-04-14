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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    EditText edtUser;
    EditText edtPassword;
    Button btnSignUp, btnLogIn, btnSkipLoggin;
    boolean logStatus = false;
    FirebaseDatabase database;
    DatabaseReference users;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");
        auth = FirebaseAuth.getInstance();
        edtUser = (EditText)findViewById(R.id.edtUser);
        edtPassword = (EditText)findViewById(R.id.edtPassword);

        btnLogIn = (Button)findViewById(R.id.btn_log_in);
        btnSignUp = (Button)findViewById(R.id.btn_sign_up);
        btnSkipLoggin = (Button)findViewById(R.id.btn_Skip);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivity();
            }
        });

        btnSkipLoggin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSkipLoggin();
            }
        });

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtUser.getText().toString();
                String pw = edtPassword.getText().toString();
                logIn(email,pw);

            }
        });
    }

    //Skippa loggin och gå direkt till Home
    public void openSkipLoggin() {
        Intent intent = new Intent(this, Home.class);
        System.out.println("Fungerar");
        startActivity(intent);
    }

    //Öppna RegActivity view
    public void openNewActivity(){
        Intent intent = new Intent(this, RegActivity.class);
        startActivity(intent);
    }
    //Öppna Home view
    public void openHomeActivity() {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

    //Kollar så man skriver in rätt saker i rätt fält samt loggar in
    private void logIn(String user, String pw) {
        auth.signInWithEmailAndPassword(user,pw).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(MainActivity.this, "Inloggning lyckad", Toast.LENGTH_SHORT).show();
                openHomeActivity();
                finish();
            }
        });
        }
    }
