package com.example.covidscanner.ui.reports;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ReportsViewModelFactory implements ViewModelProvider.Factory {


    public ReportsViewModelFactory() {
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ReportsViewModel.class)) {
            return (T) new ReportsViewModel();
        }

        throw new IllegalArgumentException("Unknown ViewModel class");
    }

}
