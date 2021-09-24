package com.example.covidscanner.ui.heartrate;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.example.covidscanner.R;
import com.example.covidscanner.databinding.ActivityHeartRateBinding;
import com.example.covidscanner.ui.base.BaseActivity;

public class HeartRateActivity extends BaseActivity<HeartRateViewModel> implements HeartRateNavigator {

    ActivityHeartRateBinding binding;

    @NonNull
    @Override
    protected HeartRateViewModel createViewModel() {
        HeartRateViewModelFactory factory = new HeartRateViewModelFactory();
        return ViewModelProviders.of(this, factory).get(HeartRateViewModel.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setDataBindings();
        viewModel.setNavigator(this);
    }

    private void setDataBindings() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_heart_rate);
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