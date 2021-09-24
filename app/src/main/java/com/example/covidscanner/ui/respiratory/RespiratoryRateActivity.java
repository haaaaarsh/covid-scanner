package com.example.covidscanner.ui.respiratory;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.example.covidscanner.R;
import com.example.covidscanner.databinding.ActivityHeartRateBinding;
import com.example.covidscanner.databinding.ActivityRespiRateBinding;
import com.example.covidscanner.ui.base.BaseActivity;

public class RespiratoryRateActivity extends BaseActivity<RespiratoryRateViewModel> implements RespiratoryRateNavigator {

    ActivityRespiRateBinding binding;

    @NonNull
    @Override
    protected RespiratoryRateViewModel createViewModel() {
        RespiratoryRateViewModelFactory factory = new RespiratoryRateViewModelFactory();
        return ViewModelProviders.of(this, factory).get(RespiratoryRateViewModel.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setDataBindings();
        viewModel.setNavigator(this);
    }

    private void setDataBindings() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_respi_rate);
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