package com.example.covidscanner.ui.reports;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covidscanner.R;
import com.example.covidscanner.data.model.Symptoms;
import com.example.covidscanner.databinding.ItemReportBinding;
import com.example.covidscanner.databinding.ItemSymptomBinding;
import com.example.covidscanner.ui.symptom.SymptomsModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ViewHolder> {
    private List<Symptoms> symptomsList;
    private Activity context;
    private LayoutInflater inflater;
    private ReportClickListener reportClickListener;

    public ReportAdapter(Activity context, ReportClickListener reportClickListener) {
        this.context = context;
        this.symptomsList = new ArrayList<>();
        this.reportClickListener = reportClickListener;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ReportAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemReportBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_report, parent, false);
        return new ReportAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ReportAdapter.ViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return symptomsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ItemReportBinding binding;

        ViewHolder(ItemReportBinding bind) {
            super(bind.getRoot());
            this.binding = bind;
        }

        void bindData(final int position) {
            binding.time.setText(symptomsList.get(position).creationDate);
            binding.rlRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reportClickListener.onReportClick(symptomsList.get(position));
                }
            });
        }
    }

    public List<Symptoms> getList() {
        return this.symptomsList;
    }

    public void addAll(List<Symptoms> list) {
        this.symptomsList.addAll(list);
        notifyDataSetChanged();
    }

    public void clearAll() {
        this.symptomsList.clear();
        notifyDataSetChanged();
    }

    public interface ReportClickListener {
        public void onReportClick(Symptoms symptom);
    }
}