package com.codepath.myrecipes;

import android.app.Application;

import com.parse.Parse;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("GjxGurxacNwoJdIycRWSDUepjxSWskbOXnA5dNEu")
                .clientKey("rxDplM9870gRQ2eY5BJkXN9D8QYVG3D3o7jnDtVu")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
