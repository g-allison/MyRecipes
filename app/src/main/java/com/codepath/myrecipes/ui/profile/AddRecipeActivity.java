package com.codepath.myrecipes.ui.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.codepath.myrecipes.R;
import com.codepath.myrecipes.ui.WeeklyMenu;

public class AddRecipeActivity extends AppCompatActivity {

    public static final String TAG = "AddRecipeActivity";

    private TextView mDayName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        mDayName = findViewById(R.id.tvDayName);

        WeeklyMenu day = getIntent().getParcelableExtra("recipe card");
        mDayName.setText(day.getDay());
    }
}