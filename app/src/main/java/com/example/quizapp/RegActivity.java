package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import java.util.ArrayList;
import java.util.List;

import static com.example.quizapp.R.drawable.roundedeterror;


public class RegActivity extends AppCompatActivity {
    EditText edtUser, edtNewPassword, edtNewEmail;
    TextView tvLoggin;
    Button btn_sign_up;
    private FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference users, categories;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        List<String> catNames;
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");
        mDatabase = FirebaseDatabase.getInstance().getReference();
        edtUser = (EditText) findViewById(R.id.edtUser);
        edtNewEmail = (EditText) findViewById(R.id.edtNewEmail);
        edtNewPassword = (EditText) findViewById(R.id.edtFriendSearch);
        Spinner category = (Spinner) findViewById(R.id.spinner_fav_cat);
        tvLoggin = (TextView) findViewById(R.id.tv_loggin);
        btn_sign_up = (Button) findViewById(R.id.btn_sign_up);
        catNames = new ArrayList<>();
        categories = FirebaseDatabase.getInstance().getReference();

        //Visar alla kategorier i vår spinner
        categories.child("Category").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    String spinnerName = childSnapshot.child("Name").getValue(String.class);
                    if(!spinnerName.equals("Dagens Quiz")){
                        catNames.add(spinnerName);
                    }
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(RegActivity.this, android.R.layout.simple_spinner_item, catNames);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                category.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Går till Main
        tvLoggin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMain();
            }
        });

        //Skapar kontot
        btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String favCategory = category.getSelectedItem().toString();
                System.out.println(favCategory);
                String email = edtNewEmail.getText().toString();
                String pw = edtNewPassword.getText().toString();
                String username = edtUser.getText().toString();
                //Olika felmeddelanden baserat på vad felet är.
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pw) || TextUtils.isEmpty(username)) {
                    Toast.makeText(RegActivity.this, "Kom ihåg att fylla alla fält", Toast.LENGTH_SHORT).show();
                } else if (pw.length() < 6) {
                    Toast.makeText(RegActivity.this, "Lösenordet är inte starkt nog!", Toast.LENGTH_SHORT).show();
                    edtNewPassword.setBackgroundResource(roundedeterror);

                } else if (username.length() > 12) {
                    Toast.makeText(RegActivity.this, "Användarnamn får vara max 12 bokstäver!", Toast.LENGTH_SHORT).show();
                    edtUser.setBackgroundResource(roundedeterror);
                } else {
                    writeNewUser(email, pw, favCategory);
                }
            }
        });
    }
    //Metoden för att skapa nya kontot
    public void writeNewUser(String email, String pw, String cat) {
        String userName = edtUser.getText().toString();
        userName = userName.substring(0, 1).toUpperCase() + userName.substring(1).toLowerCase();
        String finalUserName = userName;
        users.orderByChild("userName").equalTo(userName).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (!snapshot.exists()) {
                    auth.createUserWithEmailAndPassword(email, pw).addOnCompleteListener(RegActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                String uId = FirebaseAuth.getInstance().getUid();
                                User user = new User(finalUserName, uId, cat);
                                users.child(user.getUID()).setValue(user);
                                Toast.makeText(RegActivity.this, "Konto regsisterat", Toast.LENGTH_SHORT).show();
                                openMain();
                                finish();

                            } else {
                                edtNewEmail.setBackgroundResource(roundedeterror);
                                Toast.makeText(RegActivity.this, "Konto registrering misslyckad", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } else {
                    edtUser.setBackgroundResource(roundedeterror);
                    Toast.makeText(RegActivity.this, "Användarnamn måste vara unikt", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });

    }

    //Gå till main view
    public void openMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    //Kollar om användarnamnet redan finns
    private boolean usernameExists(String username) {
        DatabaseReference fdbRefer = FirebaseDatabase.getInstance().getReference("Users/" + username);
        return (fdbRefer != null);
    }

}