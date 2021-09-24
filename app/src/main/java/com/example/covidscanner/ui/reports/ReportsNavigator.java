package com.example.covidscanner.ui.reports;

import android.content.Context;

public interface ReportsNavigator {

    Context getActivityContext();

    void onError(String message);
}
