package com.example.covidscanner.ui.respiratory;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.covidscanner.R;
import com.example.covidscanner.data.db.AppDatabase;
import com.example.covidscanner.data.db.dao.SymptomsDao;
import com.example.covidscanner.data.model.Symptoms;
import com.example.covidscanner.databinding.ActivityHeartRateBinding;
import com.example.covidscanner.databinding.ActivityRespiRateBinding;
import com.example.covidscanner.ui.base.BaseActivity;
import com.example.covidscanner.utils.services.MeasureRespirationService;

public class RespiratoryRateActivity extends BaseActivity<RespiratoryRateViewModel> implements RespiratoryRateNavigator {

    ActivityRespiRateBinding binding;
    AppDatabase db;
    Intent service;

    @NonNull
    @Override
    protected RespiratoryRateViewModel createViewModel() {
        RespiratoryRateViewModelFactory factory = new RespiratoryRateViewModelFactory();
        return ViewModelProviders.of(this, factory).get(RespiratoryRateViewModel.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolBar();
        setDataBindings();
        viewModel.setNavigator(this);
        db = AppDatabase.getInstance();
    }


    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(
                mMessageReceiver, new IntentFilter("RespiratoryRate"));
        service = new Intent(this, MeasureRespirationService.class);
        startService(service);
    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            double message = intent.getDoubleExtra("value", 0);
            class UpdateRespiTask extends AsyncTask<Void, Void, Void> {
                @Override
                protected Void doInBackground(Void... voids) {
                    SymptomsDao symptomsDao = db.symptomsDao();
                    Symptoms s = symptomsDao.getSymptoms().get(symptomsDao.getSymptoms().size() - 1);
                    s.respiRate = (float) message;
                    symptomsDao.updateSymptom(s);
                    return null;
                }
            }
            UpdateRespiTask updateRespiTask = new UpdateRespiTask();
            updateRespiTask.execute();
            stopService(service);
            binding.btnReport.setText(String.format("Complete: %.2f", message));
        }
    };

    private void setToolBar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.title_respi_rate);
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