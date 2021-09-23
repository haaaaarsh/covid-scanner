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

        AppDatabase db = AppDatabase.getInstance();
        UserDao userDao = db.userDao();
        class InsertUser extends AsyncTask <Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                userDao.insertUser(new User("haaarsh", "12323", "K", "H"));
                List<User> user = userDao.getUser();
                return null;
            }
        }
        InsertUser insertUser = new InsertUser();
        insertUser.execute();
    }

    private void setDataBindings() {
        ActivitySplashBinding activitySplashBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        activitySplashBinding.setViewModel(viewModel);
        activitySplashBinding.executePendingBindings();
    }
}