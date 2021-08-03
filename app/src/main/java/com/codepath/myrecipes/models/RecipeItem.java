package com.codepath.myrecipes.models;

import androidx.annotation.NonNull;

public class RecipeItem {

    private String mId;
    private String mTitle;
    private String mThumbnailUrl;
    private int mAmountOfDishes;
    private int mReadyInMins;

    public RecipeItem(String id, String title, String thumbnail, int amountOfDishes, int readyInMins) {
        this.mId = id;
        mTitle = title;
        mThumbnailUrl = thumbnail;
        this.mAmountOfDishes = amountOfDishes;
        this.mReadyInMins = readyInMins;
    }

    public String getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getThumbnail() {
        return mThumbnailUrl;
    }

    public int getAmountOfDishes() {
        return mAmountOfDishes;
    }

    public int getReadyInMins() {
        return mReadyInMins;
    }

    @NonNull
    @Override
    public String toString() {
        return getTitle();
    }
}