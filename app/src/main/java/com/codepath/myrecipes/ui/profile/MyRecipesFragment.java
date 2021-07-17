package com.codepath.myrecipes.ui.profile;

import android.content.Intent;
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
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.codepath.myrecipes.Post;
import com.codepath.myrecipes.R;
import com.codepath.myrecipes.ui.home.PostActivity;
import com.codepath.myrecipes.ui.home.PostsAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.jetbrains.annotations.NotNull;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyRecipesFragment extends Fragment implements PostsAdapter.OnPostListener{

    public static final String TAG = "MyRecipesFragment";

    private RecyclerView mRvPosts;
    private PostsAdapter mPostsAdapter;
    private List<Post> mPosts;

    public MyRecipesFragment() {
        // Required empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_recipes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRvPosts = view.findViewById(R.id.rvPosts);

        // creating posts recycler view
        mPosts = new ArrayList<>();
        mPostsAdapter = new PostsAdapter(getContext(), mPosts, this);
        mRvPosts.setAdapter(mPostsAdapter);
        mRvPosts.setLayoutManager(new LinearLayoutManager(mRvPosts.getContext()));

        queryPosts();

    }

    private void queryPosts() {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.setLimit(20);
        query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());
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

                mPosts.addAll(posts);
                mPostsAdapter.notifyDataSetChanged();

//                Post post = mPosts.get(0);
//                Glide.with(view.getContext())
//                        .load(post.getProfile().getUrl())
//                        .circleCrop()
//                        .into(mIvProfilePicture);
            }
        });
    }

    @Override
    public void onPostClick(int position) {
        Log.d(TAG, "onTweetClick: clicked");
        Intent intent = new Intent(getContext(), PostActivity.class);
        intent.putExtra("post", Parcels.wrap(mPosts.get(position)));
        startActivity(intent);
        Log.d(TAG, "startActivity");
    }
}