package com.codepath.myrecipes.models;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Register your parse models
        ParseObject.registerSubclass(Post.class);
        ParseObject.registerSubclass(WeeklyMenu.class);
        ParseObject.registerSubclass(Recipes.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("GjxGurxacNwoJdIycRWSDUepjxSWskbOXnA5dNEu")
                .clientKey("rxDplM9870gRQ2eY5BJkXN9D8QYVG3D3o7jnDtVu")
                .server("https://parseapi.back4app.com")
                .build()
        );

    }
}
