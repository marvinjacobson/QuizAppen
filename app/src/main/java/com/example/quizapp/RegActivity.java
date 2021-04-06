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
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizapp.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class RegActivity extends AppCompatActivity {
    EditText edtNewUser, edtNewPassword, edtNewEmail;

    TextView tvLoggin;
    Button btn_sign_up;

    FirebaseDatabase database;
    DatabaseReference users;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");

        edtNewUser = (EditText)findViewById(R.id.edtUser);
        edtNewEmail = (EditText)findViewById(R.id.edtNewEmail);
        edtNewPassword = (EditText)findViewById(R.id.edtPassword);

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
               createAccount();
            }
        });


    }
    //Gå till main view
    public void openMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    //Skickar username, lösenord och mail till firebase.
    public void createAccount(){
            User user = new User(edtNewUser.getText().toString(), edtNewPassword.getText().toString(), edtNewEmail.getText().toString());

            users.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.child(user.getUserName()).exists())
                        Toast.makeText(RegActivity.this, "User exists", Toast.LENGTH_SHORT).show();
                    else {
                        users.child(user.getUserName())
                                .setValue(user);
                        Toast.makeText(RegActivity.this, "User registration success", Toast.LENGTH_SHORT).show();
                        openMain();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

}}