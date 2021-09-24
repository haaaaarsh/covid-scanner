package com.example.covidscanner.ui.respiratory;

import com.example.covidscanner.data.db.AppDatabase;
import com.example.covidscanner.ui.base.BaseViewModel;

public class RespiratoryRateViewModel extends BaseViewModel<RespiratoryRateNavigator> {

    AppDatabase db;

    public RespiratoryRateViewModel() {
        db = AppDatabase.getInstance();
    }

}
