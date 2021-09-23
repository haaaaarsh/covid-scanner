package com.example.covidscanner.ui.splash;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.example.covidscanner.R;
import com.example.covidscanner.databinding.ActivityLoginBinding;
import com.example.covidscanner.databinding.ActivitySplashBinding;
import com.example.covidscanner.ui.base.BaseActivity;

public class SplashActivity extends BaseActivity<SplashViewModel> {

    @NonNull
    @Override
    protected SplashViewModel createViewModel() {
        SplashViewModelFactory factory = new SplashViewModelFactory();
        return ViewModelProviders.of(this, factory).get(SplashViewModel.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDataBindings();
    }

    private void setDataBindings() {
        ActivitySplashBinding activitySplashBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        activitySplashBinding.setViewModel(viewModel);
        activitySplashBinding.executePendingBindings();
    }
}