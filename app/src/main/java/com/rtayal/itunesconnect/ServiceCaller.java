package com.rtayal.itunesconnect;

import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by rtayal on 12/18/17.
 */

public class ServiceCaller implements Callback {

    private static final OkHttpClient client = new OkHttpClient();

    private static final String BASE_URL_CLOUD = "http://review-monitor.herokuapp.com";
    //    private static final String BASE_URL_CLOUD_DEV = "https://cottonbackend-dev.herokuapp.com";
    private static final String BASE_URL_LOCAL = "http://10.0.2.2:4567";

    private CallbackOnMain callbackOnMain;

    private static String BASE_URL() {
        if (android.os.Debug.isDebuggerConnected()) {
            return BASE_URL_LOCAL;
        } else {
            return BASE_URL_CLOUD;
        }
    }

    void loginUser(String username, String password, CallbackOnMain callback) {
        RequestBody reqbody = RequestBody.create(null, new byte[0]);
        Request request = new Request.Builder()
                .url(BASE_URL() + "/login?username=" + username + "&password=" + password)
                .method("POST", reqbody)
                .build();
        callbackOnMain = callback;
        client.newCall(request).enqueue(this);
    }

    void getApps(CallbackOnMain callback) {
        SharedPreferences preferences = MyApplication.sharedPreferences();
        String email = preferences.getString(Helper.SharedPrefUserNameKey, "");
        String password = preferences.getString(Helper.SharedPrefPasswordKey, "");
        Request request = new Request.Builder()
                .url(BASE_URL() + "/apps?username=" + email + "&password=" + password)
                .build();
        callbackOnMain = callback;
        client.newCall(request).enqueue(this);
    }

    @Override
    public void onResponse(final Call call, final Response response) throws IOException {
        final String body = response.body().string();
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                try {
                    callbackOnMain.onResponse(call, body);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onFailure(final Call call, final IOException e) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                callbackOnMain.onFailure(call, e);
            }
        });
    }
}

interface CallbackOnMain {
    void onFailure(Call call, IOException e);

    void onResponse(Call call, String response) throws IOException;
}