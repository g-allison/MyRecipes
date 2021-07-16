package com.codepath.myrecipes.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.codepath.myrecipes.InstructionsFragment;
import com.codepath.myrecipes.Post;
import com.codepath.myrecipes.R;
import com.codepath.myrecipes.databinding.ActivityPostBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.parceler.Parcels;

import java.util.ArrayList;

public class PostActivity extends AppCompatActivity {

    public static final String TAG = "PostActivity";

    private TextView mTvDescription;
    private ImageView mIvImage;

    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: called");

        ActivityPostBinding binding = ActivityPostBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        tabLayout.addTab(tabLayout.newTab().setText("Ingredients"));
        tabLayout.addTab(tabLayout.newTab().setText("Instructions"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        Post post = (Post) Parcels.unwrap(getIntent().getParcelableExtra("post"));
        Log.d(TAG, "onCreate: post contents" + post);

        mTvDescription = binding.tvDescription;
        mIvImage = binding.ivImage;

        mTvDescription.setText(post.getDescription());

        Glide.with(this)
                .load(post.getImage().getUrl())
                .centerCrop()
                .into(mIvImage);


        Intent intent = new Intent(this, InstructionsFragment.class);
        intent.putExtra("post", post);

        final TabAdapter tabAdapter = new TabAdapter(this.getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(tabAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

}
