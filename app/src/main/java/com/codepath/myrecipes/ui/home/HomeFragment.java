package com.codepath.myrecipes.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.codepath.myrecipes.models.Post;
import com.codepath.myrecipes.R;
import com.codepath.myrecipes.ui.openingScreen.MainActivity;
import com.codepath.myrecipes.ui.postActivity.PostActivity;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import static com.parse.Parse.getApplicationContext;

public class HomeFragment extends Fragment implements PostsAdapter.OnPostListener {

    public static final String TAG = "HomeFragment";

    private RecyclerView mRvPosts;
    private PostsAdapter mAdapter;
    private List<Post> mAllPosts;
    private SwipeRefreshLayout mSwipeContainer;

    private TextView mTvSearch;
    private TextView mTvEmpty;
    private ImageButton mBtnSearch;
    private ProgressBar mProgressBar;

    public HomeFragment() {
        // Required empty public constructor
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // ###
        mTvEmpty = view.findViewById(R.id.empty_view2);
        mProgressBar = view.findViewById(R.id.progressbar2);
        mTvSearch = view.findViewById(R.id.home_search_et);
        mBtnSearch = view.findViewById(R.id.home_search_btn);
        mBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search = mTvSearch.getText().toString();
                if (!search.equals("")) {
                    mTvEmpty.setVisibility(View.GONE);
                    mProgressBar.setVisibility(View.VISIBLE);
                    findIngredient(search);
                } else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.type_something), Toast.LENGTH_LONG).show();
                }
                mSwipeContainer.setRefreshing(false);
            }
        });


        mRvPosts = view.findViewById(R.id.rvDays);

        mAllPosts = new ArrayList<>();
        mAdapter = new PostsAdapter(getContext(), mAllPosts, this);

        mRvPosts.setAdapter(mAdapter);
        mRvPosts.setLayoutManager(new LinearLayoutManager(getContext()));

        mSwipeContainer = view.findViewById(R.id.swipeContainer);

        mSwipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // clearing out old items before appending in the new ones
                mAdapter.clear();
                // ...the data has come back, adding new items to your adapter...
                queryPosts();
                // calling setRefreshing(false) to signal refresh has finished
                mSwipeContainer.setRefreshing(false);
            }
        });
        // Configuring the refreshing colors
        mSwipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        queryPosts();
    }

    private void findIngredient(String search) {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.setLimit(20);
        query.addDescendingOrder(Post.KEY_CREATED_KEY);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                // checks for errors
                if (e != null) {
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }

                mAdapter.clear();
                for (Post post : posts) {
                    Log.d(TAG, "post ingredients: " + post.getDescription() + ", " + post.getIngredients().toString());
                    if (post.getIngredients().toString().contains(search)) {
                        mAllPosts.add(post);
                        Log.d(TAG, post.getDescription() + " added");
                    }
                }

                mProgressBar.setVisibility(View.GONE);
                Log.d(TAG, "ending mAllPosts: " + mAllPosts);
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    private void queryPosts() {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.setLimit(20);
        query.addDescendingOrder(Post.KEY_CREATED_KEY);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                // checks for errors
                if (e != null) {
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }

                for (Post post : posts) {
                    Log.i(TAG, "Post: " + post.getDescription() + ", username: " + post.getUser().getUsername());
                }
                mAllPosts.addAll(posts);
                mAdapter.notifyDataSetChanged();
            }
        });
    }


    // testing for user activity
    @Override
    public void onPostClick(ParseUser user) {
        if (!user.getUsername().equals(ParseUser.getCurrentUser().getUsername())) {
            ((MainActivity)getActivity()).profileTransition(user);
        } else {
            ((MainActivity)getActivity()).personalProfileTransition();
        }
    }
}