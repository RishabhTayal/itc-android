package com.rtayal.itunesconnect;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.widget.EditText;

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

//    private static final String BASE_URL_CLOUD = "http://review-monitor.herokuapp.com";
    //    private static final String BASE_URL_CLOUD_DEV = "https://cottonbackend-dev.herokuapp.com";
//    private static final String BASE_URL_LOCAL = "http://10.0.2.2:4567";

    private CallbackOnMain callbackOnMain;

    static String getBaseUrl() {
        SharedPreferences preferences = MyApplication.sharedPreferences();
        String url = preferences.getString("base_url", "");
        return url;
    }

    static void setBaseUrl(String url) {
        SharedPreferences.Editor editor = MyApplication.sharedPreferences().edit();
        editor.putString("base_url", url);
        editor.commit();
    }

    static void askForBaseUrl(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder((Context) activity);

        final EditText edittext = new EditText(activity);
        builder.setMessage("Enter Your Message");
        builder.setTitle("Enter Your Title");

        builder.setView(edittext);
        builder.setPositiveButton("Yes Option", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String url = edittext.getText().toString();
                ServiceCaller.setBaseUrl(url);
            }
        });
        builder.show();
    }

    void loginUser(String username, String password, CallbackOnMain callback) {
        RequestBody reqbody = RequestBody.create(null, new byte[0]);
        Request request = new Request.Builder()
                .url(ServiceCaller.getBaseUrl() + "login/v2")
                .method("POST", reqbody)
                .addHeader("username", username)
                .addHeader("password", password)
                .build();
        callbackOnMain = callback;
        client.newCall(request).enqueue(this);
    }

    void getApps(CallbackOnMain callback) {
        SharedPreferences preferences = MyApplication.sharedPreferences();
        String email = preferences.getString(Helper.SharedPrefUserNameKey, "");
        String password = preferences.getString(Helper.SharedPrefPasswordKey, "");
        String teamId = preferences.getString(Helper.SharedPrefTeamIdKey, "");
        Request request = new Request.Builder()
                .url(ServiceCaller.getBaseUrl() + "apps")
                .addHeader("username", email)
                .addHeader("password", password)
                .addHeader("team_id", teamId)
                .build();
        callbackOnMain = callback;
        client.newCall(request).enqueue(this);
    }

    void getReviews(String bundle_id, String storeFront, CallbackOnMain callback) {
        SharedPreferences preferences = MyApplication.sharedPreferences();
        String email = preferences.getString(Helper.SharedPrefUserNameKey, "");
        String password = preferences.getString(Helper.SharedPrefPasswordKey, "");
        String teamId = preferences.getString(Helper.SharedPrefTeamIdKey, "");
        Request request = new Request.Builder()
                .url(ServiceCaller.getBaseUrl() + "ratings?bundle_id=" + bundle_id + "&store_front=" + storeFront)
                .addHeader("username", email)
                .addHeader("password", password)
                .addHeader("team_id", teamId)
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