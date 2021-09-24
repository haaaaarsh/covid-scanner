package com.example.covidscanner.ui.respiratory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class RespiratoryRateViewModelFactory implements ViewModelProvider.Factory {


    public RespiratoryRateViewModelFactory() {
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(RespiratoryRateViewModel.class)) {
            return (T) new RespiratoryRateViewModel();
        }

        throw new IllegalArgumentException("Unknown ViewModel class");
    }

}
