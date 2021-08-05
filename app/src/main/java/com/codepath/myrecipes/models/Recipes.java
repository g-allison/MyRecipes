package com.codepath.myrecipes.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.ArrayList;

@ParseClassName("Recipes")
public class Recipes extends ParseObject {
    public static final String KEY_RECIPE_NAME = "recipeName";
    public static final String KEY_IMAGE_URL = "imageUrl";
    public static final String KEY_INSTRUCTIONS = "instructions";
    public static final String KEY_INGREDIENTS = "ingredients";

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

    public void setSteps(Object[] steps) {
        put(KEY_INSTRUCTIONS, steps);
    }

    public void setIngredients(ArrayList ingredients) {
        put(KEY_INGREDIENTS, ingredients);
    }

    public Object getIngredients() {
        return getList(KEY_INGREDIENTS);
    }
}
