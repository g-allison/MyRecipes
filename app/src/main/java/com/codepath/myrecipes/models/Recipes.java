package com.codepath.myrecipes.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Recipes")
public class Recipes extends ParseObject {
    public static final String KEY_RECIPE_NAME = "recipeName";
    public static final String KEY_IMAGE_URL = "imageUrl";

    public String getRecipeName() {
        return getString(KEY_RECIPE_NAME);
    }

    public void setRecipeName(String name) {
        put(KEY_RECIPE_NAME, name);
    }

    public String getImageUrl() {
        return getString(KEY_IMAGE_URL);
    }

    public void setImageUrl(String imageUrl) {
        put(KEY_IMAGE_URL, imageUrl);
    }

}
