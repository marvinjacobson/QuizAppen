package com.example.quizapp;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizapp.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.quizapp.R.drawable.roundedeterror;


public class RegActivity extends AppCompatActivity {
    EditText edtUser, edtNewPassword, edtNewEmail;
    TextView tvLoggin;
    Button btn_sign_up;
    private FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference users;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");
        mDatabase = FirebaseDatabase.getInstance().getReference();
        edtUser = (EditText)findViewById(R.id.edtUser);
        edtNewEmail = (EditText)findViewById(R.id.edtNewEmail);
        edtNewPassword = (EditText)findViewById(R.id.edtPassword);
        auth = FirebaseAuth.getInstance();
        tvLoggin = (TextView)findViewById(R.id.tv_loggin);
        btn_sign_up = (Button)findViewById(R.id.btn_sign_up);

        tvLoggin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMain();
            }
        });
        btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String email = edtNewEmail.getText().toString();
               String pw = edtNewPassword.getText().toString();
               String username = edtUser.getText().toString();
               if(TextUtils.isEmpty(email) || TextUtils.isEmpty(pw) || TextUtils.isEmpty(username)){
                   Toast.makeText(RegActivity.this, "Kom ihåg att fylla alla fält", Toast.LENGTH_SHORT).show();
            }
               else if(pw.length() < 6){
                   Toast.makeText(RegActivity.this, "Lösenordet är inte starkt nog!", Toast.LENGTH_SHORT).show();
                   edtNewPassword.setBackgroundResource(roundedeterror);

               }
               else if(username.length() > 12){
                   Toast.makeText(RegActivity.this, "Användarnamn får vara max 12 bokstäver!", Toast.LENGTH_SHORT).show();
                   edtUser.setBackgroundResource(roundedeterror);
               }
               else{
                   writeNewUser(email, pw);

               }
            }
        });
    }
    public void writeNewUser(String email, String pw) {
        String uId = FirebaseAuth.getInstance().getUid();
        String userName = edtUser.getText().toString();
        userName = userName.substring(0,1).toUpperCase() + userName.substring(1).toLowerCase();

        User user = new User(userName, uId);
        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.child(user.getUserName()).exists()){
                    edtUser.setBackgroundResource(roundedeterror);
                    Toast.makeText(RegActivity.this, "Användarnamn måste vara unikt", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    auth.createUserWithEmailAndPassword(email,pw).addOnCompleteListener(RegActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){

                            users.child(user.getUserName()).setValue(user);
                            Toast.makeText(RegActivity.this, "Konto regsisterat", Toast.LENGTH_SHORT).show();
                            openMain();


                        }
                        else{
                            edtNewEmail.setBackgroundResource(roundedeterror);
                            Toast.makeText(RegActivity.this, "Konto registrering misslyckad", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

    }

    private void registerUser(String email, String pw) {
        auth.createUserWithEmailAndPassword(email,pw).addOnCompleteListener(RegActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    String uId = FirebaseAuth.getInstance().getUid();
                    String userName = edtUser.getText().toString();
                    writeNewUser(uId, userName);
                    Toast.makeText(RegActivity.this, "Konto regsisterat", Toast.LENGTH_SHORT).show();
                    openMain();
                }
                else{
                    Toast.makeText(RegActivity.this, "Konto registrering misslyckad", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    //Gå till main view
    public void openMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}