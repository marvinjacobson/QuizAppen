package com.example.quizapp.ViewHolder;

import android.content.ClipData;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizapp.Interface.ItemClickListener;
import com.example.quizapp.R;

public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView category_name;


    private ItemClickListener itemClickListener;


    public CategoryViewHolder(@NonNull View itemView) {
        super(itemView);

        category_name = (TextView) itemView.findViewById(R.id.categoryName);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);
    }

}
