package com.marc0x71.mvpsample;

import android.app.Application;
import android.util.Log;

/**
 * Created on 16/06/2016.
 */
public class App extends Application {

    private static final String TAG = "App";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
    }
}
