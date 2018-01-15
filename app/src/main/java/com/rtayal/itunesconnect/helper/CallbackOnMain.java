package com.rtayal.itunesconnect.helper;

import java.io.IOException;

import okhttp3.Call;

/**
 * Created by rtayal on 1/15/18.
 */

public interface CallbackOnMain {
    void onFailure(Call call, IOException e);

    void onResponse(Call call, String response) throws IOException;
}