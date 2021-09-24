package com.example.covidscanner.ui.dashboard;

import com.example.covidscanner.data.db.AppDatabase;
import com.example.covidscanner.ui.base.BaseViewModel;

public class DashboardViewModel extends BaseViewModel<DashboardNavigator> {

    AppDatabase db;

    public DashboardViewModel() {
        db = AppDatabase.getInstance();
    }

    public void openNextScreen(int screenNum) {
        getNavigator().openNextScreen(screenNum);
    }

    public void logout() {
        getNavigator().logout();
    }
}
