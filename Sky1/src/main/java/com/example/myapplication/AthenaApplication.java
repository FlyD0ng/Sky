package com.example.myapplication;

import android.app.Application;

/**
 * Created by MJ on 2017/1/25.
 */

public class AthenaApplication extends Application {
    public static AthenaApplication AnthenaApp;

    public static AthenaApplication getApplication() {

        return AnthenaApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AnthenaApp = this;

    }
}