package com.example.covidscanner.ui.symptom;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.example.covidscanner.R;
import com.example.covidscanner.data.db.AppDatabase;
import com.example.covidscanner.data.db.dao.UserDao;
import com.example.covidscanner.data.model.User;
import com.example.covidscanner.databinding.ActivitySymptomBinding;
import com.example.covidscanner.ui.base.BaseActivity;
import com.example.covidscanner.ui.login.LoginActivity;

public class SymptomActivity extends BaseActivity<SymptomViewModel> implements SymptomNavigator {

    ActivitySymptomBinding binding;

    @NonNull
    @Override
    protected SymptomViewModel createViewModel() {
        SymptomViewModelFactory factory = new SymptomViewModelFactory();
        return ViewModelProviders.of(this, factory).get(SymptomViewModel.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDataBindings();
        viewModel.setNavigator(this);
    }

    private void setDataBindings() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_symptom);
        binding.setViewModel(viewModel);
        binding.executePendingBindings();
    }

    @Override
    public Context getActivityContext() {
        return this;
    }

    @Override
    public void onError(String message) {
        showSnackbar(message, Color.RED, Color.WHITE);
    }

}