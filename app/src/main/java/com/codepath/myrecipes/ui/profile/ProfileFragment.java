package com.codepath.myrecipes.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.codepath.myrecipes.Post;
import com.codepath.myrecipes.R;
import com.codepath.myrecipes.ui.WeeklyMenu;
import com.codepath.myrecipes.ui.home.PostActivity;
import com.codepath.myrecipes.ui.home.PostsAdapter;
import com.google.android.material.tabs.TabLayout;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {

    public static final String TAG = "ProfileFragment";

    private ImageView mIvProfilePicture;
    private TextView mTvUsername;
    private Button mBtnFollow;

    TabLayout tabLayout;
    ViewPager viewPager;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mIvProfilePicture = view.findViewById(R.id.ivProfilePicture);
        mTvUsername = view.findViewById(R.id.tvUsername);
        mBtnFollow = view.findViewById(R.id.btnFollow);

        ParseUser user = ParseUser.getCurrentUser();
        mTvUsername.setText(user.getUsername());

        // initializing tab header and titles
        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPager);
        // TODO: add tab titles to strings xml
        tabLayout.addTab(tabLayout.newTab().setText("Weekly Menu"));
        tabLayout.addTab(tabLayout.newTab().setText("My Recipes"));
        tabLayout.addTab(tabLayout.newTab().setText("Shopping list"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        // creating tab pages
        final ProfileTabAdapter tabAdapter = new ProfileTabAdapter(this.getActivity().getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(tabAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });
        queryForUser(view);
    }

    private void queryForUser(View view) {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.setLimit(1);
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

                Post post = posts.get(0);
                Glide.with(view.getContext())
                        .load(post.getProfile().getUrl())
                        .circleCrop()
                        .into(mIvProfilePicture);
            }
        });
    }
}