package com.example.covidscanner.ui.dashboard;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.covidscanner.data.db.AppDatabase;
import com.example.covidscanner.data.db.dao.SymptomsDao;
import com.example.covidscanner.data.model.Symptoms;
import com.example.covidscanner.ui.base.BaseViewModel;

import java.util.List;

public class DashboardViewModel extends BaseViewModel<DashboardNavigator> {

    AppDatabase db;

    public DashboardViewModel() {
        db = AppDatabase.getInstance();
    }

    public void openNextScreen(int screenNum) {
        getNavigator().openNextScreen(screenNum);
    }

    public void logout() {
        getNavigator().logout();
    }

    public void initSymptomData() {
        class InitSymptomsTask extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                Symptoms symptoms = new Symptoms(-1, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0);
                SymptomsDao symptomsDao = db.symptomsDao();
                symptomsDao.deleteByUserId(-1);
                symptomsDao.insertSymptoms(symptoms);
                return null;
            }
        }
        InitSymptomsTask initSymptomsTask = new InitSymptomsTask();
        initSymptomsTask.execute();
    }
}
