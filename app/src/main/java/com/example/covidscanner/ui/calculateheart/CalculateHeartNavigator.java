package com.example.covidscanner.ui.calculateheart;

import android.content.Context;

public interface CalculateHeartNavigator {

    Context getActivityContext();

    void onError(String message);
}
