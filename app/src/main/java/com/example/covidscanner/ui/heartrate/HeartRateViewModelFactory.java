package com.example.covidscanner.ui.heartrate;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class HeartRateViewModelFactory implements ViewModelProvider.Factory {


    public HeartRateViewModelFactory() {
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(HeartRateViewModel.class)) {
            return (T) new HeartRateViewModel();
        }

        throw new IllegalArgumentException("Unknown ViewModel class");
    }

}
