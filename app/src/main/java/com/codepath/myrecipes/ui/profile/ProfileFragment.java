package com.codepath.myrecipes.ui.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.request.target.Target;
import com.codepath.myrecipes.R;
import com.codepath.myrecipes.ui.WeeklyMenu;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {

    public static final String TAG = "ProfileFragment";

    private RecyclerView mRvDays;
    private ProfileAdapter mAdapter;
    // TODO: rename list
    private List<WeeklyMenu> mItems;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRvDays = view.findViewById(R.id.rvDays);

        mItems = new ArrayList<>();

        mAdapter = new ProfileAdapter(getContext(), mItems);

        mRvDays.setAdapter(mAdapter);
        mRvDays.setLayoutManager(new LinearLayoutManager(getContext()));

        // TODO: rename method
        queryItems();
    }

    private void queryItems() {
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
                mItems.addAll(days);
                mAdapter.notifyDataSetChanged();
            }
        });
    }
}