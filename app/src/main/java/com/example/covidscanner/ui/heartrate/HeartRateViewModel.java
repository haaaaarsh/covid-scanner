package com.example.covidscanner.ui.heartrate;

import com.example.covidscanner.data.db.AppDatabase;
import com.example.covidscanner.ui.base.BaseViewModel;

public class HeartRateViewModel extends BaseViewModel<HeartRateNavigator> {

    AppDatabase db;

    public HeartRateViewModel() {
        db = AppDatabase.getInstance();
    }

    public void record() {
        getNavigator().recordClick();
    }

}
