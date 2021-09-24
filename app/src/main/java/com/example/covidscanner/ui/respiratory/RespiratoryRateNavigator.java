package com.example.covidscanner.ui.respiratory;

import android.content.Context;

public interface RespiratoryRateNavigator {

    Context getActivityContext();

    void onError(String message);
}
