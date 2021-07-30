package com.codepath.myrecipes.ui.search;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.myrecipes.R;
import com.codepath.myrecipes.ui.openingScreen.MainActivity;
import com.codepath.myrecipes.ui.profile.add.AddRecyclerViewAdapter;
import com.parse.Parse;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static com.parse.Parse.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements SearchRecyclerViewAdapter.OnSearchListener {

    public static final String TAG = "SearchFragment";

    private TextView mTvSearch;
    private TextView mTvEmpty;
    private ImageButton mBtnSearch;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private List<ParseUser> mSearchUser;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTvEmpty = view.findViewById(R.id.empty_view2);
        mProgressBar = view.findViewById(R.id.progressbar2);
        mRecyclerView = view.findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mProgressBar.setVisibility(View.GONE);

        mTvSearch = view.findViewById(R.id.home_search_et);
        mBtnSearch = view.findViewById(R.id.home_search_btn);
        mBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search = mTvSearch.getText().toString();
                if (!search.equals("")) {
                    mTvEmpty.setVisibility(View.GONE);
                    mProgressBar.setVisibility(View.VISIBLE);
                    mRecyclerView.setAlpha(0);
                    searchUser(search);
                } else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.type_something), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void searchUser(String search) {
        mSearchUser = new ArrayList<>();
        Log.d(TAG, "searchUser: " + search);
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("username", search);
        query.findInBackground((users, e) -> {
            if (e == null) {
                Log.d(TAG, "searchUser: successful query");
                // query was successful
                for(ParseUser user : users) {
                    Log.d("User List: ", (user.getUsername()));
                    mSearchUser.add(user);
                }

                mProgressBar.setVisibility(View.GONE);
                if (mSearchUser.isEmpty()) {
                    mRecyclerView.setAlpha(0);
                    mTvEmpty.setVisibility(View.VISIBLE);
                }
                else {
                    mTvEmpty.setVisibility(View.GONE);
                    SearchRecyclerViewAdapter myAdapter = new SearchRecyclerViewAdapter(getApplicationContext(), mSearchUser, this);
                    mRecyclerView.setAdapter(myAdapter);
                    mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                    mRecyclerView.setAlpha(1);
                }
            } else {
                // Something went wrong.
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public void onSearchClick(ParseUser user) {
        if (!user.getUsername().equals(ParseUser.getCurrentUser().getUsername())) {
            ((MainActivity)getActivity()).profileTransition(user);
        } else {
            ((MainActivity)getActivity()).personalProfileTransition();
        }
    }
}