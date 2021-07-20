package com.codepath.myrecipes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.parceler.Parcels;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class IngredientsFragment extends Fragment {
    private static final String TAG = "IngredientsFragment";
    private TextView mTvIngredients;

    public IngredientsFragment() {
        // required empty constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ingredients, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Post post = (Post) Parcels.unwrap(getActivity().getIntent().getParcelableExtra("post"));
        Log.d(TAG, "onViewCreated: post " + post);

        mTvIngredients = view.findViewById(R.id.tvIngredients);

        ArrayList<String> ingredientsList = (ArrayList<String>) post.getIngredients();
        StringBuilder sb = new StringBuilder();
        if (post.getIngredients() != null) {
            for (int i = 0; i < ingredientsList.size(); i++) {
                String ingredient = ingredientsList.get(i);
                if (i != 0) {
                    sb.append(getResources().getString(R.string.single_tab));
                }
                sb.append(getResources().getString(R.string.bullet));
                sb.append(ingredient);
                sb.append(getResources().getString(R.string.single_tab));
            }
            mTvIngredients.setText(sb);
        }
    }
}