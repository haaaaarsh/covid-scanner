package com.example.covidscanner.ui.dashboard;

import android.content.Context;

public interface DashboardNavigator {

    Context getActivityContext();

    void onError(String message);

    void logout();
}
