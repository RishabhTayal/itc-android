package com.rtayal.itunesconnect.helper;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import io.fabric.sdk.android.Fabric;

/**
 * Created by rtayal on 12/21/17.
 */

public class MyApplication extends Application {

    static MyApplication mainApplication;
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Answers(), new Crashlytics());
        mainApplication = this;
        context = getApplicationContext();
    }

    public static SharedPreferences sharedPreferences() {
        return mainApplication.getSharedPreferences(Helper.SharedPrefKey, MODE_PRIVATE);
    }
}
