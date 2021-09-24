package com.example.covidscanner.ui.dashboard;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.example.covidscanner.R;
import com.example.covidscanner.data.db.AppDatabase;
import com.example.covidscanner.data.db.dao.UserDao;
import com.example.covidscanner.data.model.User;
import com.example.covidscanner.databinding.ActivityDashboardBinding;
import com.example.covidscanner.ui.base.BaseActivity;
import com.example.covidscanner.ui.login.LoginActivity;

public class DashboardActivity extends BaseActivity<DashboardViewModel> implements DashboardNavigator {

    ActivityDashboardBinding binding;

    @NonNull
    @Override
    protected DashboardViewModel createViewModel() {
        DashboardViewModelFactory factory = new DashboardViewModelFactory();
        return ViewModelProviders.of(this, factory).get(DashboardViewModel.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDataBindings();
        viewModel.setNavigator(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuLogout:
                viewModel.logout();
                break;
        }
        return true;
    }

    private void setDataBindings() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard);
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

    @Override
    public void logout() {
        class LogoutTask extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                AppDatabase db = AppDatabase.getInstance();
                UserDao userDao = db.userDao();
                User user = null;
                user = getCurrentUser();
                user.isLoggedIn = false;
                userDao.updateUser(user);
                openActivity(LoginActivity.class);
                finish();
                return null;
            }
        }
        LogoutTask logoutTask = new LogoutTask();
        logoutTask.execute();
    }


    @Override
    public void openNextScreen(int screenNum) {
        switch (screenNum) {
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
        }
    }

}