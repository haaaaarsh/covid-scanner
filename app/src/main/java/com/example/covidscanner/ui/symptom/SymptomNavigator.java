package com.example.covidscanner.ui.symptom;

import android.content.Context;

public interface SymptomNavigator {

    Context getActivityContext();

    void onError(String message);

}
