package com.example.covidscanner.ui.symptom;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class SymptomViewModelFactory implements ViewModelProvider.Factory {


    public SymptomViewModelFactory() {
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SymptomViewModel.class)) {
            return (T) new SymptomViewModel();
        }

        throw new IllegalArgumentException("Unknown ViewModel class");
    }

}
