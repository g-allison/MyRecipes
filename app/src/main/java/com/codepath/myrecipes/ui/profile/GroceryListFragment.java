package com.codepath.myrecipes.ui.profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codepath.myrecipes.models.Post;
import com.codepath.myrecipes.R;
import com.codepath.myrecipes.models.WeeklyMenu;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroceryListFragment extends Fragment {

    public static final String TAG = "GroceryListFragment";

    private TextView mTvGroceries;

    public GroceryListFragment() {
        // Required empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_grocery_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTvGroceries = view.findViewById(R.id.tvGroceries);

        queryDays();
    }

    private void queryDays() {
        StringBuilder week = new StringBuilder();

        ParseQuery<WeeklyMenu> query = ParseQuery.getQuery(WeeklyMenu.class);
        query.include(WeeklyMenu.KEY_DAY);
        query.setLimit(7);
        query.addAscendingOrder(WeeklyMenu.KEY_CREATED_AT);
        query.findInBackground(new FindCallback<WeeklyMenu>() {
            @Override
            public void done(List<WeeklyMenu> days, ParseException e) {
                // checks for errors
                if (e != null) {
                    Log.e(TAG, "Issue with WeeklyMenu query", e);
                    return;
                }

                for (WeeklyMenu dayOfWeek : days) {
                    Log.i(TAG, "day: " + dayOfWeek.getDay());
                    if (dayOfWeek.getRecipeItem() != null) {
                        ParseQuery<WeeklyMenu> query2 = ParseQuery.getQuery(WeeklyMenu.class);
                        query2.include(WeeklyMenu.KEY_RECIPE);
                        query2.getInBackground(dayOfWeek.getObjectId(), (object, e2) -> {
                            if (e2 != null) {
                                Log.e(TAG, "bind: issue with query", e2);
                            } else {
                                Log.d(TAG, "done: dayOfWeek = " + dayOfWeek);
                                Post post = object.getRecipeItem();
                                Log.d(TAG, "done: post name = " + post.getDescription());

                                ArrayList<String> ingredients = (ArrayList<String>) post.getIngredients();
                                Log.d(TAG, "done: ingredients = " + ingredients);


                                for (String ingredient : ingredients) {
                                    week.append(getResources().getString(R.string.bullet));
                                    week.append(ingredient);
                                    week.append(getResources().getString(R.string.single_tab));
                                }

                                Log.d(TAG, "week now: " + week);
                                mTvGroceries.setText(week.toString());
                            }
                        });
                    }
                }
            }
        });
    }
}