package com.example.covidscanner.ui.symptom;

import com.example.covidscanner.data.db.AppDatabase;
import com.example.covidscanner.ui.base.BaseViewModel;

public class SymptomViewModel extends BaseViewModel<SymptomNavigator> {

    AppDatabase db;

    public SymptomViewModel() {
        db = AppDatabase.getInstance();
    }

}
