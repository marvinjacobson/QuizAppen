package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class SettingsActivity extends AppCompatActivity {

    private Button btnChangePW,btnChangeUserSettings,btnLoggout,btnDeleteAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        btnChangePW = (Button)findViewById(R.id.btn_Changepw);
        btnChangeUserSettings = (Button)findViewById(R.id.btn_changeusersettings);
        btnLoggout = (Button)findViewById(R.id.btn_logout);
        btnDeleteAccount = (Button)findViewById(R.id.btn_deleteaccount);

        btnChangePW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openChangePW();
            }
        });
        btnChangeUserSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openChangeUserSettings();
            }
        });

        btnLoggout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(SettingsActivity.this, "Du är nu utloggad", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SettingsActivity.this, MainActivity.class));
            }
        });
    }

    //Öppnar ChangePW
    public void openChangePW() {
        Intent intent = new Intent(this, Changepw.class);
        startActivity(intent);
    }
    //Öppnar ChangeUserSettings
    public void openChangeUserSettings() {
        Intent intent = new Intent(this, ChangeUserSettings.class);
        startActivity(intent);
    }
}