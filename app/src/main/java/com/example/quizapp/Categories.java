package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.quizapp.Interface.ItemClickListener;
import com.example.quizapp.Model.Category;
import com.example.quizapp.ViewHolder.CategoryViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Categories extends AppCompatActivity {

    RecyclerView listCategory;
    FirebaseDatabase database;
    DatabaseReference categories;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<Category, CategoryViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        database = FirebaseDatabase.getInstance();
        categories = database.getReference("Category");
        listCategory = (RecyclerView) findViewById(R.id.listOfCat);
        listCategory.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(Categories.this);
        listCategory.setLayoutManager(layoutManager);

        loadCategories();

    }

    private void loadCategories() {
        adapter = new FirebaseRecyclerAdapter<Category, CategoryViewHolder>(
                Category.class,
                R.layout.category_layout,
                CategoryViewHolder.class,
                categories
        ) {
            @Override
            protected void populateViewHolder(CategoryViewHolder categoryViewHolder, Category category, int i) {
                categoryViewHolder.category_name.setText(category.getName());

                categoryViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        openPlay();
                    }
                });
            }
        };
        adapter.notifyDataSetChanged();
        listCategory.setAdapter(adapter);
    }

    public void openPlay() {
        Intent intent = new Intent(this, Start.class);
        startActivity(intent);
    }



}