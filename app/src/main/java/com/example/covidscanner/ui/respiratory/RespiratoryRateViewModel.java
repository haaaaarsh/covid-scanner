package com.example.covidscanner.ui.respiratory;

import androidx.databinding.ObservableField;

import com.example.covidscanner.data.db.AppDatabase;
import com.example.covidscanner.ui.base.BaseViewModel;

public class RespiratoryRateViewModel extends BaseViewModel<RespiratoryRateNavigator> {

    AppDatabase db;

    private ObservableField<String> respirRate = new ObservableField<>();

    public RespiratoryRateViewModel() {
        db = AppDatabase.getInstance();
    }

    public ObservableField<String> getRespirRate() {
        return respirRate;
    }

    public void setRespirRate(String respirRate) {
        this.respirRate.set(respirRate);
    }
}
