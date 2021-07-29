package com.codepath.myrecipes.ui.postActivity;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.codepath.myrecipes.ui.postActivity.IngredientsFragment;
import com.codepath.myrecipes.ui.postActivity.InstructionsFragment;


public class RecipeTabAdapter extends FragmentPagerAdapter {

    int tabCount;

    public RecipeTabAdapter(@NonNull FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.tabCount = numberOfTabs;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new IngredientsFragment();
            case 1:
                return new InstructionsFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}