package com.codepath.myrecipes.ui.openingScreen;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.codepath.myrecipes.R;
import com.codepath.myrecipes.ui.home.HomeFragment;
import com.codepath.myrecipes.ui.compose.DashboardFragment;
import com.codepath.myrecipes.ui.profile.OtherProfileFragment;
import com.codepath.myrecipes.ui.profile.ProfileFragment;
import com.codepath.myrecipes.ui.search.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.codepath.myrecipes.databinding.ActivityMainBinding;
import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    public static final String PARAM_INTENT_KEY = "main_activity_params";
    public static final int ADD_RECIPE_ACTIVITY_REQUEST_CODE = 200;

    public final FragmentManager mFragmentManager = getSupportFragmentManager();
    private BottomNavigationView mBottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());

        View view = binding.getRoot();
        setContentView(view);

        mBottomNavigationView = binding.bottomNavigation;
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment;
                switch (menuItem.getItemId()) {
                    case R.id.navigation_home:
                        fragment = new HomeFragment();
                        break;
                    case R.id.navigation_dashboard:
                        fragment = new DashboardFragment();
                        break;
                    case R.id.navigation_search:
                        fragment = new SearchFragment();
                        break;
                    case R.id.navigation_profile:
                    default:
                        fragment = new ProfileFragment();
                        break;
                }
                mFragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                return true;
            }
        });
        // Set default selection
        mBottomNavigationView.setSelectedItemId(R.id.navigation_home);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_RECIPE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){
            Log.d(TAG, "onActivityResult:  AddRecipeActivity came back okayyy!");
            personalProfileTransition();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.btn_logout) {
            ParseUser.logOut();
            ParseUser currentUser = ParseUser.getCurrentUser();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    public void postTransition() {
        mFragmentManager.beginTransaction().replace(R.id.flContainer, new HomeFragment()).commit();
        mBottomNavigationView.setSelectedItemId(R.id.navigation_home);
    }

    public void profileTransition(ParseUser user) {
        mFragmentManager.beginTransaction().replace(R.id.flContainer, new OtherProfileFragment(user)).commit();
    }

    public void personalProfileTransition() {
        mFragmentManager.beginTransaction().replace(R.id.flContainer, new ProfileFragment()).commit();
        mBottomNavigationView.setSelectedItemId(R.id.navigation_profile);
    }
}