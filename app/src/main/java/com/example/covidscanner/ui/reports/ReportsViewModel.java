package com.example.covidscanner.ui.reports;

import com.example.covidscanner.data.db.AppDatabase;
import com.example.covidscanner.ui.base.BaseViewModel;

public class ReportsViewModel extends BaseViewModel<ReportsNavigator> {

    AppDatabase db;

    public ReportsViewModel() {
        db = AppDatabase.getInstance();
    }

}
