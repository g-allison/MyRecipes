package com.codepath.myrecipes.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.codepath.myrecipes.Post;
import com.codepath.myrecipes.databinding.ActivityPostBinding;
import com.codepath.myrecipes.ui.post.OnSwipeTouchListener;

import org.parceler.Parcels;

import java.util.ArrayList;

public class PostActivity extends AppCompatActivity {

    public static final String TAG = "PostActivity";

    private TextView mTvUsername;
    private TextView mTvDescription;
    private TextView mTvSteps;
    private TextView mTvIngredients;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: called");

        ActivityPostBinding binding = ActivityPostBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Post post = (Post) Parcels.unwrap(getIntent().getParcelableExtra("post"));
        Log.d(TAG, "onCreate: tweet contents" + post);

        ArrayList<String> instructions = (ArrayList<String>) post.getKeySteps();

        mTvUsername = binding.tvUsername;
        mTvSteps = binding.tvSteps;
        mTvDescription = binding.tvDescription;
        mTvIngredients = binding.tvIngredients;

        mTvUsername.setText(post.getUser().getUsername());
        mTvDescription.setText(post.getDescription());
        StringBuilder sb = new StringBuilder();
        if (post.getKeySteps() != null) {
            for (int i = 0; i < instructions.size(); i++) {
                String step = instructions.get(i);
                sb.append(i);
                sb.append(". ");
                sb.append(step);
                sb.append("\n");
            }
            mTvSteps.setText(sb);
        }
//
//        ArrayList<String> ingredients = (ArrayList<String>) post.getKeyIngredients();
//
//        sb = new StringBuilder();
//        if (post.getKeyIngredients() != null) {
//            for (int i = 0; i < ingredients.size(); i++) {
//                String step = ingredients.get(i);
//                sb.append(i);
//                sb.append(". ");
//                sb.append(step);
//                sb.append("\n");
//            }
//            mTvSteps.setText(sb);
//        }
    }
}
