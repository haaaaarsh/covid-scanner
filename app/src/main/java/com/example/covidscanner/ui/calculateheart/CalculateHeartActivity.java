package com.example.covidscanner.ui.calculateheart;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.example.covidscanner.R;
import com.example.covidscanner.databinding.ActivityCalculateRateBinding;
import com.example.covidscanner.databinding.ActivityReportsBinding;
import com.example.covidscanner.ui.base.BaseActivity;

public class CalculateHeartActivity extends BaseActivity<CalculateHeartViewModel> implements CalculateHeartNavigator {

    ActivityCalculateRateBinding binding;
    String filepath;

    @NonNull
    @Override
    protected CalculateHeartViewModel createViewModel() {
        CalculateHeartViewModelFactory factory = new CalculateHeartViewModelFactory();
        return ViewModelProviders.of(this, factory).get(CalculateHeartViewModel.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolBar();
        setDataBindings();
        viewModel.setNavigator(this);
        if (getIntent() != null)
            filepath = getIntent().getStringExtra("filepath");
    }

    private void setToolBar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.title_calculate_rate);
    }

    private void setDataBindings() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_calculate_rate);
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