package com.example.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.quizapp.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
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
    FirebaseDatabase database;
    DatabaseReference users;
    private boolean logginstatus;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logginstatus = false;
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

        //Skriver felmeddelanden om du har skrivit fel användarnamn eller lösenord
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtUser.getText().toString();
                String pw = edtPassword.getText().toString();
                if(TextUtils.isEmpty(email)){
                    edtUser.setBackgroundResource(R.drawable.roundedeterror);
                    Toast.makeText(MainActivity.this, "Vänligen fyll i din mail adress", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(pw)){
                    edtPassword.setBackgroundResource(R.drawable.roundedeterror);
                    Toast.makeText(MainActivity.this, "Vänligen fyll i ditt lösenord", Toast.LENGTH_SHORT).show();
                }
                else{
                    logIn(email,pw);
                    System.out.println(logginstatus);
                }
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
        auth.signInWithEmailAndPassword(user,pw).addOnCompleteListener(this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                           openHomeActivity();
                           finish();
                            Toast.makeText(MainActivity.this, "välkommen!", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            edtUser.setBackgroundResource(R.drawable.roundedeterror);
                            edtPassword.setBackgroundResource(R.drawable.roundedeterror);
                            Toast.makeText(MainActivity.this, "Dubbelkolla email och lösenord!", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

        }

     }

