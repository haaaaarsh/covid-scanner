package com.example.covidscanner;

import android.app.Application;

public class CovidScannerApp extends Application {

    private static CovidScannerApp mInstance;
    public static CovidScannerApp getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }
}
