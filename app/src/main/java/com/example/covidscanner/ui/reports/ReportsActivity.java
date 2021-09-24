package com.example.covidscanner.ui.reports;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.example.covidscanner.R;
import com.example.covidscanner.databinding.ActivityHeartRateBinding;
import com.example.covidscanner.databinding.ActivityReportsBinding;
import com.example.covidscanner.ui.base.BaseActivity;

public class ReportsActivity extends BaseActivity<ReportsViewModel> implements ReportsNavigator {

    ActivityReportsBinding binding;

    @NonNull
    @Override
    protected ReportsViewModel createViewModel() {
        ReportsViewModelFactory factory = new ReportsViewModelFactory();
        return ViewModelProviders.of(this, factory).get(ReportsViewModel.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolBar();
        setDataBindings();
        viewModel.setNavigator(this);
    }

    private void setToolBar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.title_reports);
    }

    private void setDataBindings() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reports);
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