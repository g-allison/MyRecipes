package com.codepath.myrecipes.ui.profile;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.codepath.myrecipes.models.Post;
import com.codepath.myrecipes.R;
import com.google.android.material.tabs.TabLayout;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class ProfileFragment extends Fragment {

    public static final String TAG = "ProfileFragment";
    final int GALLERY_REQUEST = 100;

    private ImageView mIvProfilePicture;
    private ImageView mIvSettings;
    private TextView mTvUsername;
    private TextView mTvFollowerCount;
    private TextView mTvFollowingCount;
    private Button mBtnViewProfile;
    private View view;

    private Post overallPost;

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

        this.view = view;
        mIvProfilePicture = view.findViewById(R.id.ivProfilePicture);
        mTvUsername = view.findViewById(R.id.tvUsername);
//        mBtnFollow = view.findViewById(R.id.btnFollow);
        mIvSettings = view.findViewById(R.id.ivSettings);
        mBtnViewProfile = view.findViewById(R.id.btnViewProfile);

        overallPost = new Post();

        ParseUser user = ParseUser.getCurrentUser();
        mTvUsername.setText(user.getUsername());

        // initializing tab header and titles
        tabLayout = view.findViewById(R.id.tabLayout);
        //viewPager = view.findViewById(R.id.viewPager);

        /// #####
        //tabLayout.setupWithViewPager(viewPager); // set the TabLayout with the ViewPager.

        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.weekly_menu_tab)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.my_recipes_tab)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.shopping_list_tab)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        // creating tab pages
//        ProfileTabAdapter tabAdapter = new ProfileTabAdapter(this.getActivity().getSupportFragmentManager(), tabLayout.getTabCount());
        // viewPager.setAdapter(tabAdapter);
        // viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                Log.d(TAG, "onTabSelected position clicked: " + tab.getPosition());
//
//                Fragment fragment = null;
//                switch (tab.getPosition()) {
//                    case 0:
//                        Log.d("ProfileTabAdapter", "position = 0: WeeklyMenuFrag being returned");
//                        fragment = new WeeklyMenuFragment();
//                        break;
//                    case 1:
//                        Log.d("ProfileTabAdapter", "position = 1: MyRecipesFrag being returned");
//                        fragment = new MyRecipesFragment();
//                        break;
//                    case 2:
//                        Log.d("ProfileTabAdapter", "position = 2: GroceryListFrag being returned");
//                        fragment = new GroceryListFragment();
//                        break;
//                    default:
//                        Log.e(TAG, "tab position was not valid");
//                }
//                FragmentManager fm = getActivity().getSupportFragmentManager();
//                FragmentTransaction ft = fm.beginTransaction();
//                ft.replace(R.id.simpleFrameLayout, fragment);
//                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//                ft.commit();
                onTabReselected(tab);
            }


            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Log.d(TAG, "onTabReselected position clicked: " + tab.getPosition());
                Fragment fragment = null;
                switch (tab.getPosition()) {
                    case 0:
                        Log.d("ProfileTabAdapter", "position = 0: WeeklyMenuFrag being returned");
                        fragment =  new WeeklyMenuFragment();
                        break;
                    case 1:
                        Log.d("ProfileTabAdapter", "position = 1: MyRecipesFrag being returned");
                        fragment = new MyRecipesFragment();
                        break;
                    case 2:
                        Log.d("ProfileTabAdapter", "position = 2: GroceryListFrag being returned");
                        fragment =  new GroceryListFragment();
                        break;
                    default:
                        Log.e(TAG, "tab position was not valid");
                }
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.simpleFrameLayout, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }
        });

        mIvProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
            }
        });

        mIvSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SettingsActivity.class);
                startActivity(intent);
            }
        });
        queryForUser();
        //viewPager.setCurrentItem(0);
        tabLayout.selectTab(tabLayout.getTabAt(0));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode){
                case GALLERY_REQUEST:
                    Uri selectedImage = data.getData();
                    try {
                        convertToParse(selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

            }
        }
    }

    private void convertToParse(Uri selectedImage) throws IOException {
        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
        Glide.with(view.getContext())
                .load(bitmap)
                .circleCrop()
                .into(mIvProfilePicture);
        setUser(bitmap);


        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, byteArrayOutputStream);
        byte[] imageByte = byteArrayOutputStream.toByteArray();
        ParseFile parseFile = new ParseFile(getResources().getString(R.string.image_file), imageByte);
        Log.d(TAG, "onActivityResult: parseFile" + parseFile);

        overallPost.setProfile(parseFile);

        Log.d(TAG, "onActivityResult: file = " + bitmap.toString());

        overallPost.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Error while saving", e);
                    Toast.makeText(getContext(), getResources().getString(R.string.saving_error_message), Toast.LENGTH_SHORT).show();
                }
                Log.d(TAG, "done: file" + parseFile.getUrl());
            }
        });
    }

    private void setUser(Bitmap bitmap) {
//        Log.d(TAG, "setUser: doing query for user");
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.setLimit(1);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                // checks for errors
                if (e != null) {
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }
                Post post = posts.get(0);
                post.setProfile(conversionBitmapParseFile(bitmap));
                mIvProfilePicture.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        });
    }

    public ParseFile conversionBitmapParseFile(Bitmap imageBitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
        byte[] imageByte = byteArrayOutputStream.toByteArray();
        return new ParseFile(getResources().getString(R.string.image_file), imageByte);
    }

    private void queryForUser() {
//        Log.d(TAG, "queryForUser: querying for user profile picture");
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

                overallPost = posts.get(0);
                Glide.with(view.getContext())
                        .load(overallPost.getProfile().getUrl())
                        .circleCrop()
                        .into(mIvProfilePicture);
            }
        });
    }


}