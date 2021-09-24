package com.example.covidscanner.ui.splash;

import android.content.Context;
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
import com.example.covidscanner.ui.dashboard.DashboardActivity;
import com.example.covidscanner.ui.login.LoginActivity;

import java.util.List;

public class SplashActivity extends BaseActivity<SplashViewModel> implements SplashNavigator{

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
        viewModel.setNavigator(this);
        decideNextActivity();
    }

    private void decideNextActivity() {
        class CheckSessionTask extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                AppDatabase db = AppDatabase.getInstance();
                UserDao userDao = db.userDao();
                if (userDao.isLoggedIn() != null && !userDao.isLoggedIn().isEmpty()) {
                    User user = userDao.isLoggedIn().get(0);
                    setCurrentUser(user);
                    openActivity(DashboardActivity.class);
                    finish();
                } else {
                    openActivity(LoginActivity.class);
                    finish();
                }
                return null;
            }
        }
        CheckSessionTask checkSessionTask = new CheckSessionTask();
        checkSessionTask.execute();
    }

    private void setDataBindings() {
        ActivitySplashBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        binding.setViewModel(viewModel);
        binding.executePendingBindings();
    }

    @Override
    public Context getActivityContext() {
        return this;
    }
}