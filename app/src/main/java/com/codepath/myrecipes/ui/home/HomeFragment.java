package com.codepath.myrecipes.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.codepath.myrecipes.MainActivity;
import com.codepath.myrecipes.Post;
import com.codepath.myrecipes.R;
import com.codepath.myrecipes.databinding.FragmentHomeBinding;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.jetbrains.annotations.NotNull;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements PostsAdapter.OnPostListener {

    public static final String TAG = "HomeFragment";

    private RecyclerView mRvPosts;
    private PostsAdapter mAdapter;
    private List<Post> mAllPosts;
    private SwipeRefreshLayout mSwipeContainer;

    public HomeFragment() {
        // Required empty public constructor
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRvPosts = view.findViewById(R.id.rvPosts);

        mAllPosts = new ArrayList<>();
        mAdapter = new PostsAdapter(getContext(), mAllPosts, this);

        mRvPosts.setAdapter(mAdapter);
        mRvPosts.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvPosts.addItemDecoration(new DividerItemDecoration(mRvPosts.getContext(), DividerItemDecoration.VERTICAL));

        mSwipeContainer = view.findViewById(R.id.swipeContainer);

        mSwipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // clearing out old items before appending in the new ones
                mAdapter.clear();
                // ...the data has come back, add new items to your adapter...
                queryPosts();
                // Now we call setRefreshing(false) to signal refresh has finished
                mSwipeContainer.setRefreshing(false);
            }
        });
        // Configure the refreshing colors
        mSwipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        queryPosts();
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

    @Override
    public void onPostClick(int position) {
//        ((MainActivity)getActivity()).profileTransition(user);
        Intent intent = new Intent(getContext(), PostActivity.class);
        intent.putExtra("post", Parcels.wrap(mAllPosts.get(position)));
        startActivity(intent);
    }
}