package com.rtayal.itunesconnect;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by rtayal on 12/21/17.
 */

public class MyApplication extends Application {

    static MyApplication mainApplication;
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        mainApplication = this;
        context = getApplicationContext();
    }

    public static SharedPreferences sharedPreferences() {
        return mainApplication.getSharedPreferences(Helper.SharedPrefKey, MODE_PRIVATE);
    }
}