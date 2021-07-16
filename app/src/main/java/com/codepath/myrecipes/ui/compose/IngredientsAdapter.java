package com.codepath.myrecipes.ui.compose;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class IngredientsAdapter extends StepsAdapter {
    public IngredientsAdapter(List<String> items, OnLongClickListener longClickListener) {
        super(items, longClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        String item = items.get(position);
        // Bind the iem into the specified view holder
        holder.bind(item);
    }


}
