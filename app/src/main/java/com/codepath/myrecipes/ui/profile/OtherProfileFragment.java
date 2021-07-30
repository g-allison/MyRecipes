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
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class OtherProfileFragment extends MyRecipesFragment {
    private static final String TAG = "OtherProfileFragment";

    private TextView mTvUsername;
    private ImageView mIvProfileImage;
    private ParseUser mUser;
    private Button mBtnFollow;
    private View view;

    private TextView mFollow;
    private TextView mUnfollow;

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

        // ####
//        mFollow = view.findViewById(R.id.follow);
//        mUnfollow = view.findViewById(R.id.unfollow);



        // creating posts recycler view
        mPosts = new ArrayList<>();
        mAdapter = new GridAdapter(getContext(), mPosts, this);
        mRvPosts.setAdapter(mAdapter);
        mRvPosts.setLayoutManager(new GridLayoutManager(mRvPosts.getContext(), NUM_COLUMNS));

        mBtnFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "followed!", Toast.LENGTH_SHORT).show();
            }
        });

        this.view = view;
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
