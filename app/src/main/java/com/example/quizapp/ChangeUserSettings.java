package com.example.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseUser;

import static com.example.quizapp.R.drawable.roundedet;
import static com.example.quizapp.R.drawable.roundedeterror;

public class ChangeUserSettings extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    EditText edtNewEmail, edtEmailAgain, edtPassAgain;
    Button btnSaveNewEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_user_settings);
        edtNewEmail = (EditText)findViewById(R.id.edtNewEmail);
        edtEmailAgain = (EditText)findViewById(R.id.edtEmailAgain);
        edtPassAgain = (EditText)findViewById(R.id.edtPassAgain);
        btnSaveNewEmail = (Button) findViewById(R.id.btn_SaveNewEmail);
        btnSaveNewEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEmail = edtNewEmail.getText().toString();
                String currentEmailAgain = edtEmailAgain.getText().toString();
                String currentPassAgain = edtPassAgain.getText().toString();
                //Kollar så man inte skriver in fel saker i fel rutor
                if (!TextUtils.isEmpty(newEmail) && !TextUtils.isEmpty(currentEmailAgain) && !TextUtils.isEmpty(currentPassAgain) && !newEmail.equals(currentEmailAgain)){
                    reauthenticate(newEmail, currentPassAgain, currentEmailAgain);
                    edtNewEmail.setBackgroundResource(roundedet);
                    edtPassAgain.setBackgroundResource(roundedet);
                    edtEmailAgain.setBackgroundResource(roundedet);
                }
                if (TextUtils.isEmpty(newEmail)){
                    Toast.makeText(ChangeUserSettings.this, "Kom ihåg att fylla i en ny mail", Toast.LENGTH_SHORT).show();
                    edtNewEmail.setBackgroundResource(roundedeterror);
                }
                if (TextUtils.isEmpty(currentEmailAgain)){
                    Toast.makeText(ChangeUserSettings.this, "Kom ihåg att fylla i din nuvarande mail", Toast.LENGTH_SHORT).show();
                    edtEmailAgain.setBackgroundResource(roundedeterror);
                }
                if (TextUtils.isEmpty(currentPassAgain)){
                    Toast.makeText(ChangeUserSettings.this, "Kom ihåg att fylla i ditt nuvarande lösenord", Toast.LENGTH_SHORT).show();
                    edtPassAgain.setBackgroundResource(roundedeterror);
                }
                if (newEmail.equals(currentEmailAgain)){
                    Toast.makeText(ChangeUserSettings.this, "Din nya mail får inte vara samma som din nuvarande email", Toast.LENGTH_SHORT).show();
                    edtNewEmail.setBackgroundResource(roundedeterror);
                    edtEmailAgain.setBackgroundResource(roundedeterror);
                }
            }
        });
    }

    public void reauthenticate(String newEmail, String password, String oldEmail) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        // Get auth credentials from the user for re-authentication
        AuthCredential credential = EmailAuthProvider
                .getCredential(oldEmail, password); // Current Login Credentials \\
        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Log.d(TAG, "User re-authenticated.");

                            //Kod för att ändra email
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            user.updateEmail(newEmail)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d(TAG, "User email address updated.");
                                                Toast.makeText(ChangeUserSettings.this, "Din email är nu ändrad, du kommer nu bli utloggad.", Toast.LENGTH_SHORT).show();
                                                final Handler handler = new Handler(Looper.getMainLooper());
                                                handler.postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        FirebaseAuth.getInstance().signOut();
                                                        openMainActivity();
                                                    }
                                                }, 5000);
                                            }else {
                                                Toast.makeText(ChangeUserSettings.this, "Dubbelkolla nya mailadressen", Toast.LENGTH_SHORT).show();
                                                edtNewEmail.setBackgroundResource(roundedeterror);
                                            }
                                        }
                                    });
                        }else {
                            Toast.makeText(ChangeUserSettings.this, "Dubbelkolla email och lösenord", Toast.LENGTH_SHORT).show();
                            edtPassAgain.setBackgroundResource(roundedeterror);
                            edtEmailAgain.setBackgroundResource(roundedeterror);
                        }
                    }
                });
    }

    public void openMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}