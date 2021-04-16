package com.example.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SettingsActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
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
        //Logga ut när man klickar på knappen
        btnLoggout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(SettingsActivity.this, "Du är nu utloggad", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SettingsActivity.this, MainActivity.class));
                finish();
            }
        });

        //Alert som frågar om man verkligen vill radera kontot eller inte när man klickar på knappen
        btnDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(SettingsActivity.this);
                builder1.setMessage("Vill du verkligen radera ditt konto?");
                builder1.setCancelable(true);
                //Om ja, radera kontot.
                builder1.setPositiveButton(
                        "Ja",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                deleteUser();
                                openMainActivity();
                                Toast.makeText(SettingsActivity.this, "Ditt konto är nu raderat", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                //Om nej, stäng alert och radera inte kontot.
                builder1.setNegativeButton(
                        "Nej",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });
    }
    //Raderar konto
    public void deleteUser() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user.delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Kontot är raderat");
                            finish();
                        }
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
    //Går tillbaka till main
    public void openMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}