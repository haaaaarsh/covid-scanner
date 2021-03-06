package com.example.covidscanner.ui.dashboard;

import android.content.Context;

import com.example.covidscanner.data.model.User;

public interface DashboardNavigator {

    Context getActivityContext();

    void onError(String message);

    void logout();

    void openNextScreen(int screenNum);

    void createReport();

    User getUser();

    void onReportCreateSuccess();
}
