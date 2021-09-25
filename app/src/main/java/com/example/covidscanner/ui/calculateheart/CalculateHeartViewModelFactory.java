package com.example.covidscanner.ui.calculateheart;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class CalculateHeartViewModelFactory implements ViewModelProvider.Factory {


    public CalculateHeartViewModelFactory() {
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(CalculateHeartViewModel.class)) {
            return (T) new CalculateHeartViewModel();
        }

        throw new IllegalArgumentException("Unknown ViewModel class");
    }

}
