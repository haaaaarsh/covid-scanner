package com.example.covidscanner.ui.respiratory;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
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
import com.example.covidscanner.databinding.ActivityRespiRateBinding;
import com.example.covidscanner.ui.base.BaseActivity;
import com.example.covidscanner.utils.AlertUtil;
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
        openDialog();
        LocalBroadcastManager.getInstance(RespiratoryRateActivity.this).unregisterReceiver(mMessageReceiver);
        if (service != null)
            stopService(service);
    }


    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(
                mMessageReceiver, new IntentFilter("RespiratoryRate"));
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(RespiratoryRateActivity.this).unregisterReceiver(mMessageReceiver);
        if (service != null)
            stopService(service);
    }

    public void openDialog() {
        AlertUtil.showAlertDialogWithListener(this, getString(R.string.instructions_breathe), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                service = new Intent(RespiratoryRateActivity.this, MeasureRespirationService.class);
                startService(service);
                binding.circularProgressBar.setIndeterminateMode(true);
            }
        });
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            double respiRate = intent.getDoubleExtra("respiRate", 0);
            class UpdateRespiTask extends AsyncTask<Void, Void, Void> {
                @Override
                protected Void doInBackground(Void... voids) {
                    SymptomsDao symptomsDao = db.symptomsDao();
                    Symptoms s = symptomsDao.getSymptoms().get(symptomsDao.getSymptoms().size() - 1);
                    s.respiRate = (float) respiRate;
                    symptomsDao.updateSymptom(s);
                    return null;
                }

                @Override
                protected void onPostExecute(Void unused) {
                    super.onPostExecute(unused);
                    this.cancel(true);
                }
            }
            UpdateRespiTask updateRespiTask = new UpdateRespiTask();
            updateRespiTask.execute();
            if (service != null)
                stopService(service);
            binding.circularProgressBar.setIndeterminateMode(false);
            viewModel.setRespirRate(String.format("%.2f", respiRate));
            LocalBroadcastManager.getInstance(RespiratoryRateActivity.this).unregisterReceiver(mMessageReceiver);
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
        binding.txtRespiRate.setText("Breathe");
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