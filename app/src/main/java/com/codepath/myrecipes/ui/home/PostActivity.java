package com.codepath.myrecipes.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.codepath.myrecipes.IngredientsFragment;
import com.codepath.myrecipes.InstructionsFragment;
import com.codepath.myrecipes.Post;
import com.codepath.myrecipes.R;
import com.codepath.myrecipes.databinding.ActivityPostBinding;
import com.google.android.material.tabs.TabLayout;

import org.parceler.Parcels;

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
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.ingredients_tab)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.instructions_tab)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        Post post = (Post) Parcels.unwrap(getIntent().getParcelableExtra("post"));
        Log.d(TAG, "onCreate: post contents" + post);

        mTvDescription = binding.tvDescription;
        mIvImage = binding.ivImage;

        mTvDescription.setText(post.getDescription());

        if (post.getImage() != null) {
            mIvImage.setVisibility(View.VISIBLE);
            Glide.with(this)
                    .load(post.getImage().getUrl())
                    .centerCrop()
                    .into(mIvImage);
        } else {
            mIvImage.setVisibility(View.GONE);
        }

        Intent intent = new Intent(this, InstructionsFragment.class);
        intent.putExtra("post", post);

        intent = new Intent(this, IngredientsFragment.class);
        intent.putExtra("post", post);

        final RecipeTabAdapter tabAdapter = new RecipeTabAdapter(this.getSupportFragmentManager(), tabLayout.getTabCount());
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
