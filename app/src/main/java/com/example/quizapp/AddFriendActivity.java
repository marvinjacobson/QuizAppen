package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.quizapp.Model.User;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class AddFriendActivity extends AppCompatActivity {
    private EditText edt_Search;
    private RecyclerView result_list;
    private TextView tv_Hint;
    private DatabaseReference mUserDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend2);
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("User");

        tv_Hint = (TextView)findViewById(R.id.tv_Hint);
        edt_Search = (EditText)findViewById(R.id.edt_Search);
        result_list = (RecyclerView)findViewById(R.id.result_list);
        result_list.setHasFixedSize(true);
        result_list.setLayoutManager(new LinearLayoutManager(this));

        edt_Search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() >2){
                    String searchtext = edt_Search.getText().toString();
                    firebaseUserSearch(searchtext);
                    tv_Hint.setText("Klicka på en person i listan för att skicka en vänförfrågan");

                }
                else{
                    tv_Hint.setText("Var mer specifik i din sökning");
                }
            }
        });

    }

    private void firebaseUserSearch(String searchtext) {

        Query firebaseSearchQuery = mUserDatabase.orderByChild("userName").startAt(searchtext).endAt(searchtext+"\uf8ff");

        FirebaseRecyclerAdapter<User, UsersViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<User, UsersViewHolder>(
                User.class, R.layout.list_layout, UsersViewHolder.class,firebaseSearchQuery
        ) {
            @Override
            protected void populateViewHolder(UsersViewHolder usersViewHolder, User user, int i) {
                usersViewHolder.setDetails(user.getUserName());
            }
        };
        result_list.setAdapter(firebaseRecyclerAdapter);
    }


    public static class UsersViewHolder extends RecyclerView.ViewHolder{
        View mView;
        public UsersViewHolder(View itemView){
            super(itemView);
            mView = itemView;
        }
        public void setDetails(String userName){
            TextView name_text = (TextView) mView.findViewById(R.id.name_text);
            name_text.setText(userName);
        }
    }
}