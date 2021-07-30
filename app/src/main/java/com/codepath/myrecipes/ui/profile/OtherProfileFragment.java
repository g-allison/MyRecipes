package com.codepath.myrecipes.ui.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;

import com.bumptech.glide.Glide;
import com.codepath.myrecipes.R;
import com.codepath.myrecipes.models.Post;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OtherProfileFragment extends MyRecipesFragment {
    private static final String TAG = "OtherProfileFragment";

    private TextView mTvUsername;
    private ImageView mIvProfileImage;
    private ParseUser mUser;
    private Button mBtnFollow;
    private Button mBtnUnfollow;
    private View view;

    public OtherProfileFragment() {
        // required empty public constructor
    }

    public OtherProfileFragment(ParseUser user) {
        this.mUser = user;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_other_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, Bundle savedInstanceState) {
        final int NUM_COLUMNS = 3;
        super.onViewCreated(view, savedInstanceState);

        mRvPosts = view.findViewById(R.id.rvPosts);
        mIvProfileImage = view.findViewById(R.id.ivProfilePicture);
        mTvUsername = view.findViewById(R.id.tvUsername);
        mBtnFollow = view.findViewById(R.id.btnFollow);
        mBtnUnfollow = view.findViewById(R.id.btnUnfollow);

        followingVisibility();

        // creating posts recycler view
        mPosts = new ArrayList<>();
        mAdapter = new GridAdapter(getContext(), mPosts, this);
        mRvPosts.setAdapter(mAdapter);
        mRvPosts.setLayoutManager(new GridLayoutManager(mRvPosts.getContext(), NUM_COLUMNS));

        mBtnFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                follow();
            }
        });

        mBtnUnfollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unfollow();
            }
        });

        this.view = view;
    }

    private void followingVisibility() {
        List<ParseUser> nowFollowing = ParseUser.getCurrentUser().getList("following");
        Log.d(TAG, "onViewCreated: nowFollowing " + nowFollowing);
        if (nowFollowing != null) {
            boolean isFollowing = false;

            for (ParseUser user : nowFollowing) {
                try {
                    user.fetchIfNeeded();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "onViewCreated: user.getUsername() " + user.getUsername());
                Log.d(TAG, "onViewCreated: mUser.getUsername() " + mUser.getUsername());
                if (user.getUsername().equals(mUser.getUsername())) {
                    isFollowing = true;
                }
            }

            if (isFollowing) {
                mBtnFollow.setVisibility(View.GONE);
                mBtnUnfollow.setVisibility(View.VISIBLE);
            } else {
                mBtnFollow.setVisibility(View.VISIBLE);
                mBtnUnfollow.setVisibility(View.GONE);
            }
        } else {
            mBtnUnfollow.setVisibility(View.GONE);
        }
    }

    private void follow() {
        Toast.makeText(getActivity(), "followed!", Toast.LENGTH_SHORT).show();
        ParseUser currentUser = ParseUser.getCurrentUser();
        List<ParseUser> following = new ArrayList<>();
        if (currentUser.get("following") != null) {
            // user already follows some
            following.addAll(currentUser.getList("following"));
            // currentUser.get("following");
        }
        following.add(mUser);

        Log.d(TAG, "added user " + mUser.getUsername());
        currentUser.put("following", following);
        currentUser.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Error while saving", e);
                    Toast.makeText(getContext(), getResources().getString(R.string.saving_error_message), Toast.LENGTH_SHORT).show();
                }
                Log.i(TAG, "Post save was successful!");
            }
        });
        mBtnFollow.setVisibility(View.GONE);
        mBtnUnfollow.setVisibility(View.VISIBLE);
        Log.d(TAG, "currentUser now follows: " + currentUser.get("following"));
    }

    private void unfollow() {
        Toast.makeText(getActivity(), "unfollowed!", Toast.LENGTH_SHORT).show();
        ParseUser currentUser = ParseUser.getCurrentUser();
        List<ParseUser> following = new ArrayList<>(currentUser.getList("following"));

        for (int i = 0; i < following.size(); i++) {
            ParseUser user = following.get(i);
            try {
                user.fetchIfNeeded();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (user.getUsername().equals(mUser.getUsername())) {
                following.remove(user);
                i--;
            }

        }
        Log.d(TAG, "removed user " + mUser.getUsername());
        Log.d(TAG, "now following: " + following);
        currentUser.put("following", following);
        currentUser.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Error while saving", e);
                    Toast.makeText(getContext(), getResources().getString(R.string.saving_error_message), Toast.LENGTH_SHORT).show();
                }
                Log.i(TAG, "Post save was successful!");
            }
        });
        mBtnFollow.setVisibility(View.VISIBLE);
        mBtnUnfollow.setVisibility(View.GONE);
        Log.d(TAG, "currentUser now follows: " + currentUser.get("following"));

    }

    @Override
    protected void queryPosts() {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.setLimit(20);
        query.addDescendingOrder(Post.KEY_CREATED_KEY);
        query.whereEqualTo(Post.KEY_USER, mUser);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                // check for errors
                if (e != null) {
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }

                for (Post post : posts) {
                    Log.i(TAG, "Post: " + post.getDescription() + ", username: " + post.getUser().getUsername());
                }
                mPosts.addAll(posts);
                mAdapter.notifyDataSetChanged();

                mTvUsername.setText(posts.get(0).getUser().getUsername());

                Glide.with(view.getContext())
                        .load(posts.get(0).getProfile().getUrl())
                        .circleCrop()
                        .into(mIvProfileImage);
            }
        });
    }
}
