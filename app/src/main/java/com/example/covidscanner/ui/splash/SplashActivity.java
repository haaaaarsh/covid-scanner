package com.example.covidscanner.ui.splash;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.example.covidscanner.R;
import com.example.covidscanner.data.db.AppDatabase;
import com.example.covidscanner.data.db.dao.UserDao;
import com.example.covidscanner.data.model.User;
import com.example.covidscanner.databinding.ActivitySplashBinding;
import com.example.covidscanner.ui.base.BaseActivity;

import java.util.List;

public class SplashActivity extends BaseActivity<SplashViewModel> {

    ActivitySplashBinding binding;

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
        ActivitySplashBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        binding.setViewModel(viewModel);
        binding.executePendingBindings();
    }
}