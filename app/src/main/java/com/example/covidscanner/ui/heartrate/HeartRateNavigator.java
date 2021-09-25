package com.example.covidscanner.ui.heartrate;

import android.content.Context;

public interface HeartRateNavigator {

    Context getActivityContext();

    void onError(String message);

    void recordClick();
}
