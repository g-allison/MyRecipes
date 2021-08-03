package com.codepath.myrecipes.models;

import com.codepath.myrecipes.models.Post;
import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

import java.io.File;

@ParseClassName("WeeklyMenu")
public class WeeklyMenu extends ParseObject {
    public static final String KEY_DAY = "day";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_CREATED_KEY = "createdAt";
    public static final String KEY_RECIPE = "recipe";
    public static final String KEY_RECIPE_NAME = "recipeName";
    public static final String KEY_RECIPE_ITEM = "recipeItem";

    public String getDay() {
        return getString(KEY_DAY);
    }

    public ParseFile getImage() {
        return getParseFile(KEY_IMAGE);
    }

    public void setImage(ParseFile parseFile) {
        put(KEY_IMAGE, parseFile);
    }

    // shouldn't be referencing post anymore
    public Post getRecipeItem() {
        return (Post) get(KEY_RECIPE);
    }

    public Recipes getRecipe() {
        return (Recipes) get(KEY_RECIPE_ITEM);
    }

    // correct one
    public void setRecipe(Recipes recipe) {
        put(KEY_RECIPE_ITEM, recipe);
    }

    public String getRecipeName() {
        return getString(KEY_RECIPE_NAME);
    }

    public void setRecipeName(String name) {
        put(KEY_RECIPE_NAME, name);
    }
}
