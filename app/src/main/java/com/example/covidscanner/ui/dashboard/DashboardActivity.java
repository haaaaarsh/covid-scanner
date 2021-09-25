package com.example.covidscanner.ui.dashboard;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.covidscanner.R;
import com.example.covidscanner.data.db.AppDatabase;
import com.example.covidscanner.data.db.dao.SymptomsDao;
import com.example.covidscanner.data.db.dao.UserDao;
import com.example.covidscanner.data.model.Symptoms;
import com.example.covidscanner.data.model.User;
import com.example.covidscanner.databinding.ActivityDashboardBinding;
import com.example.covidscanner.ui.base.BaseActivity;
import com.example.covidscanner.ui.heartrate.HeartRateActivity;
import com.example.covidscanner.ui.login.LoginActivity;
import com.example.covidscanner.ui.reports.ReportsActivity;
import com.example.covidscanner.ui.respiratory.RespiratoryRateActivity;
import com.example.covidscanner.ui.symptom.SymptomActivity;

import java.util.List;

public class DashboardActivity extends BaseActivity<DashboardViewModel> implements DashboardNavigator {

    ActivityDashboardBinding binding;
    private final int CAMERA_PERMISSION_REQUEST_CODE = 0x01;

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
        viewModel.initSymptomData();
        setData();
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
                if (checkPermission())
                    openActivity(HeartRateActivity.class);
                break;
            case 2:
                openActivity(RespiratoryRateActivity.class);
                break;
            case 3:
                openActivity(SymptomActivity.class);
                break;
            case 4:
                openActivity(ReportsActivity.class);
                break;
        }
    }

    private void setDataBindings() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard);
        binding.setViewModel(viewModel);
        binding.executePendingBindings();
    }

    private void setData() {
        AppDatabase db = AppDatabase.getInstance();
        SymptomsDao symptomsDao = db.symptomsDao();
        symptomsDao.loadSymptoms().observe(this, new Observer<List<Symptoms>>() {
            @Override
            public void onChanged(List<Symptoms> symptoms) {
                Symptoms s = symptoms.get(0);

                binding.txtHeartRate.setText(String.valueOf(s.heartRate != 0f ? s.heartRate + " BPM" : "No Data"));
                binding.txtHeartRate.setTextColor(s.heartRate != 0f ? getColor(android.R.color.holo_green_light) : getColor(android.R.color.holo_red_light));

                binding.txtRespiRate.setText(String.valueOf(s.respiRate != 0f ? s.respiRate : "No Data"));
                binding.txtRespiRate.setTextColor(s.respiRate != 0f ? getColor(android.R.color.holo_green_light) : getColor(android.R.color.holo_red_light));

            }
        });
    }

    private boolean checkPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED ||
                checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
        ) {

            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_PERMISSION_REQUEST_CODE);
            return false;
        }

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    openActivity(HeartRateActivity.class);
                break;
        }
    }

}