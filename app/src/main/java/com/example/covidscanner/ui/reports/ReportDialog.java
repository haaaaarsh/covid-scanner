package com.example.covidscanner.ui.reports;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covidscanner.R;
import com.example.covidscanner.data.model.Symptoms;
import com.example.covidscanner.ui.symptom.SymptomsModel;

import java.util.ArrayList;
import java.util.List;

public class ReportDialog extends Dialog implements View.OnClickListener, SymptomReportAdapter.ReportClickListener {

    public Activity c;
    public Dialog d;
    public Button btnDone;
    TextView heartTxt, respiTxt;
    SymptomReportAdapter adapter;
    RecyclerView rvSymptoms;

    public ReportDialog(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.report_dialog);
        btnDone = (Button) findViewById(R.id.btnDone);
        heartTxt = (TextView) findViewById(R.id.heartTxt);
        respiTxt = (TextView) findViewById(R.id.respiTxt);
        rvSymptoms = (RecyclerView) findViewById(R.id.rvSymptoms);
        btnDone.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDone:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }

    public void setData(Symptoms symptoms) {
        heartTxt.setText(String.format("Heart Rate: %.2f", symptoms.heartRate));
        respiTxt.setText(String.format("Respiration Rate: %.2f", symptoms.respiRate));

        adapter = new SymptomReportAdapter(c, this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvSymptoms.getContext(),
                new LinearLayoutManager(c).getOrientation());
        LinearLayoutManager layoutManager = new LinearLayoutManager(c);
        rvSymptoms.setLayoutManager(layoutManager);
        rvSymptoms.addItemDecoration(dividerItemDecoration);
        rvSymptoms.setAdapter(adapter);

        List<SymptomsModel> list = new ArrayList<>();
        list.add(new SymptomsModel("Nausea", symptoms.nausea));
        list.add(new SymptomsModel("Headache", symptoms.headache));
        list.add(new SymptomsModel("Diarrhea", symptoms.diarrhea));
        list.add(new SymptomsModel("Soar Throat", symptoms.soarThroat));
        list.add(new SymptomsModel("Fever", symptoms.fever));
        list.add(new SymptomsModel("Muscle Ache", symptoms.muscleAche));
        list.add(new SymptomsModel("Loss of Smell or Taste", symptoms.smellLoss));
        list.add(new SymptomsModel("Cough", symptoms.cough));
        list.add(new SymptomsModel("Shortness of Breath", symptoms.shortnessBreath));
        list.add(new SymptomsModel("Feeling tired", symptoms.tiredness));
        updateList(list);
    }

    public void updateList(List<SymptomsModel> notificationList) {
        adapter.clearAll();
        adapter.addAll(notificationList);
    }

    @Override
    public void onReportClick(SymptomsModel symptom, float rating) {

    }
}