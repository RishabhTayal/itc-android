package com.rtayal.itunesconnect;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by rtayal on 12/18/17.
 */

public class ServiceCaller implements Callback {

    private static final OkHttpClient client = new OkHttpClient();

    private CallbackOnMain callbackOnMain;

    void getApps(CallbackOnMain callback) {
        Request request = new Request.Builder()
                .url("http://review-monitor.herokuapp.com" + "/apps?username=contact@appikon.com&password=AppikonSoft121")
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