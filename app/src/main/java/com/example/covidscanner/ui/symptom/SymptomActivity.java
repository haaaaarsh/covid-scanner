package com.example.covidscanner.ui.symptom;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.covidscanner.R;
import com.example.covidscanner.data.db.AppDatabase;
import com.example.covidscanner.data.db.dao.SymptomsDao;
import com.example.covidscanner.data.model.Symptoms;
import com.example.covidscanner.databinding.ActivitySymptomBinding;
import com.example.covidscanner.ui.base.BaseActivity;

import java.util.List;

public class SymptomActivity extends BaseActivity<SymptomViewModel> implements SymptomNavigator, SymptomsAdapter.RatingClickListener {

    ActivitySymptomBinding binding;
    SymptomsAdapter adapter;

    @NonNull
    @Override
    protected SymptomViewModel createViewModel() {
        SymptomViewModelFactory factory = new SymptomViewModelFactory();
        return ViewModelProviders.of(this, factory).get(SymptomViewModel.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setDataBindings();
        viewModel.setNavigator(this);
        setRecyclerView();
    }

    private void setDataBindings() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_symptom);
        binding.setViewModel(viewModel);
        binding.executePendingBindings();
    }

    private void setRecyclerView() {
        adapter = new SymptomsAdapter(this, this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.rvSymptoms.getContext(),
                new LinearLayoutManager(this).getOrientation());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.rvSymptoms.setLayoutManager(layoutManager);
        binding.rvSymptoms.addItemDecoration(dividerItemDecoration);
        binding.rvSymptoms.setAdapter(adapter);

        viewModel.setSymptomsList();

        viewModel.getSymptomsLiveData().observe(this, new Observer<List<SymptomsModel>>() {
            @Override
            public void onChanged(List<SymptomsModel> symptomsModels) {
                updateList(symptomsModels);
            }
        });
    }

    public void updateList(List<SymptomsModel> notificationList) {
        adapter.clearAll();
        adapter.addAll(notificationList);
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
    public void onRatingClick(SymptomsModel symptom, float rating) {
        class RatingTask extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                AppDatabase db = AppDatabase.getInstance();
                SymptomsDao symptomsDao = db.symptomsDao();
                Symptoms s = symptomsDao.getSymptoms().get(symptomsDao.getSymptoms().size() - 1);
                switch (symptom.getName()) {
                    case "Nausea":
                        s.nausea = rating;
                        break;
                    case "Headache":
                        s.headache = rating;
                        break;
                    case "Diarrhea":
                        s.diarrhea = rating;
                        break;
                    case "Soar Throat":
                        s.soarThroat = rating;
                        break;
                    case "Fever":
                        s.fever = rating;
                        break;
                    case "Muscle Ache":
                        s.muscleAche = rating;
                        break;
                    case "Loss of Smell or Taste":
                        s.smellLoss = rating;
                        break;
                    case "Cough":
                        s.cough = rating;
                        break;
                    case "Shortness of Breath":
                        s.shortnessBreath = rating;
                        break;
                    case "Feeling tired":
                        s.tiredness = rating;
                        break;
                }
                symptomsDao.updateSymptom(s);
                return null;
            }
        }
        RatingTask ratingTask = new RatingTask();
        ratingTask.execute();
    }
}