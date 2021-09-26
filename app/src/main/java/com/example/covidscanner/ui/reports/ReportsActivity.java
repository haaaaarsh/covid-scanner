package com.example.covidscanner.ui.reports;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.WindowManager;

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
import com.example.covidscanner.databinding.ActivityHeartRateBinding;
import com.example.covidscanner.databinding.ActivityReportsBinding;
import com.example.covidscanner.ui.base.BaseActivity;
import com.example.covidscanner.ui.symptom.SymptomsAdapter;
import com.example.covidscanner.ui.symptom.SymptomsModel;

import java.util.List;

public class ReportsActivity extends BaseActivity<ReportsViewModel> implements ReportsNavigator, ReportAdapter.ReportClickListener {

    ActivityReportsBinding binding;
    AppDatabase db;
    ReportAdapter adapter;

    @NonNull
    @Override
    protected ReportsViewModel createViewModel() {
        ReportsViewModelFactory factory = new ReportsViewModelFactory();
        return ViewModelProviders.of(this, factory).get(ReportsViewModel.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = AppDatabase.getInstance();
        setToolBar();
        setDataBindings();
        viewModel.setNavigator(this);
        setRecyclerView();
        setReportList();
    }

    @Override
    public Context getActivityContext() {
        return this;
    }

    @Override
    public void onError(String message) {
        showSnackbar(message, Color.RED, Color.WHITE);
    }

    private void setToolBar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.title_reports);
    }

    private void setDataBindings() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reports);
        binding.setViewModel(viewModel);
        binding.executePendingBindings();
    }

    private void setReportList() {
        SymptomsDao symptomsDao = db.symptomsDao();
        symptomsDao.findByUserId(getCurrentUser().getId()).observe(this, new Observer<List<Symptoms>>() {
            @Override
            public void onChanged(List<Symptoms> symptoms) {
                updateList(symptoms);
            }
        });
    }

    private void setRecyclerView() {
        adapter = new ReportAdapter(this, this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.rvReports.getContext(),
                new LinearLayoutManager(this).getOrientation());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.rvReports.setLayoutManager(layoutManager);
        binding.rvReports.addItemDecoration(dividerItemDecoration);
        binding.rvReports.setAdapter(adapter);
    }

    public void updateList(List<Symptoms> symptomList) {
        adapter.clearAll();
        adapter.addAll(symptomList);
    }

    @Override
    public void onReportClick(Symptoms symptom) {
        ReportDialog dialog = new ReportDialog(this);
        dialog.show();
        dialog.setData(symptom);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }
}