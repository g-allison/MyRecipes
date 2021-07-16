package com.codepath.myrecipes.ui;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

@ParseClassName("WeeklyMenu")
public class WeeklyMenu extends ParseObject {
    public static final String KEY_DAY = "day";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_CREATED_KEY = "createdAt";

    public String getDay() {
        return getString(KEY_DAY);
    }

    public ParseFile getImage() {
        return getParseFile(KEY_IMAGE);
    }

    public void setImage(ParseFile parseFile) {
        put(KEY_IMAGE, parseFile);
    }



}
