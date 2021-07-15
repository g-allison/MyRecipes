package com.codepath.myrecipes;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Post")
public class Post extends ParseObject {
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_USER = "user";
    public static final String KEY_CREATED_KEY = "createdAt";
    public static final String KEY_NAME = "name";
    public static final String KEY_PROFILE = "profilePicture";


    public String getName() {
        return getUser().getString(KEY_NAME);
    }

    public void setName(String name) {
        getUser().put(KEY_NAME, name);
    }

    public ParseFile getProfile() {
        return getUser().getParseFile(KEY_PROFILE);
    }

    public void setProfile(ParseFile parseFile) {
        getUser().put(KEY_PROFILE, parseFile);
    }

    public String getDescription() {
        return getString(KEY_DESCRIPTION);
    }

    public void setDescription(String description) {
        put(KEY_DESCRIPTION, description);
    }

    public ParseFile getImage() {
        return getParseFile(KEY_IMAGE);
    }

    public void setImage(ParseFile parseFile) {
        put(KEY_IMAGE, parseFile);
    }

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser parseUser) {
        put(KEY_USER, parseUser);
    }

}
