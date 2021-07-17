package com.codepath.myrecipes.ui.profile;

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
                return new WeeklyMenuFragment();
            case 1:
                return new MyRecipesFragment();
            case 2:
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
