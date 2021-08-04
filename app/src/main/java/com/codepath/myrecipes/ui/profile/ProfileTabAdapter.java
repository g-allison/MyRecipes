package com.codepath.myrecipes.ui.profile;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ProfileTabAdapter extends FragmentPagerAdapter {
    int tabCount;

    public ProfileTabAdapter(@NonNull FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.tabCount = numberOfTabs;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Log.d("ProfileTabAdapter", "position = 0: WeeklyMenuFrag being returned");
                return new WeeklyMenuFragment();
            case 1:
                Log.d("ProfileTabAdapter", "position = 1: MyRecipesFrag being returned");
                return new MyRecipesFragment();
            case 2:
                Log.d("ProfileTabAdapter", "position = 2: GroceryListFrag being returned");
                return new GroceryListFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
