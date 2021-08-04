package com.codepath.myrecipes.ui.profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.codepath.myrecipes.R;
import com.codepath.myrecipes.models.WeeklyMenu;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeeklyMenuFragment extends Fragment {

    public static final String TAG = "WeeklyMenuFragment";

    private RecyclerView mRvDays;
    private ProfileAdapter mDaysAdapter;
    private List<WeeklyMenu> mDays;
    private FrameLayout fragment_container;

    public WeeklyMenuFragment() {
        // Required empty constructor
    }

//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_weekly_menu, container, false);
//    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView called");
        return inflater.inflate(R.layout.fragment_weekly_menu, container, false);
    }


    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated is called");

        mRvDays = view.findViewById(R.id.rvDays);
        fragment_container = view.findViewById(R.id.fragment_container);

        // creating days recyclerview
        mDays = new ArrayList<>();
        mDaysAdapter = new ProfileAdapter(getContext(), mDays);
        mRvDays.setAdapter(mDaysAdapter);
        mRvDays.setLayoutManager(new LinearLayoutManager(getContext()));

        queryDays();
    }

    private void queryDays() {
        Log.d(TAG, "queryDays: querying for days");
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

                for (WeeklyMenu day : days) {
                    Log.i(TAG, "day: " + day.getDay());
                }
                mDays.addAll(days);
                mDaysAdapter.notifyDataSetChanged();
            }
        });
    }
}