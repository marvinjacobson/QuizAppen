package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Changepw extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    Button btnSendPassReset;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    // Mail
    String email = user.getEmail();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepw);
        btnSendPassReset = (Button) findViewById(R.id.btn_SendPassResetMail);

        btnSendPassReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendPasswordReset();
                Toast.makeText(Changepw.this, "Återställnings mail är skickat, du kommer nu loggas ut.", Toast.LENGTH_SHORT).show();
                //Loggar ut efter 5 sec och går tillbaka till inlogg sidan.
                final Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        FirebaseAuth.getInstance().signOut();
                        openMainActivity();
                    }
                }, 5000);
            }
        });
    }

    public void sendPasswordReset() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String emailAddress = email;

        auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Återställnings mail är skickat, du kommer nu loggas ut.");
                        }
                    }
                });
    }

    //Går tillbaka till main
    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}