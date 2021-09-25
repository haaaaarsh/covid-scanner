package com.example.covidscanner.ui.calculateheart;

import com.example.covidscanner.data.db.AppDatabase;
import com.example.covidscanner.ui.base.BaseViewModel;

public class CalculateHeartViewModel extends BaseViewModel<CalculateHeartNavigator> {

    AppDatabase db;

    public CalculateHeartViewModel() {
        db = AppDatabase.getInstance();
    }

}
