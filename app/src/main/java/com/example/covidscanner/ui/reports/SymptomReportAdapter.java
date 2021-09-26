package com.example.covidscanner.ui.reports;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covidscanner.R;
import com.example.covidscanner.databinding.ItemSymptomBinding;
import com.example.covidscanner.ui.symptom.SymptomsModel;

import java.util.ArrayList;
import java.util.List;

public class SymptomReportAdapter extends RecyclerView.Adapter<SymptomReportAdapter.ViewHolder> {
    private List<SymptomsModel> symptomsList;
    private Activity context;
    private LayoutInflater inflater;
    private ReportClickListener reportClickListener;

    public SymptomReportAdapter(Activity context, ReportClickListener reportClickListener) {
        this.context = context;
        this.symptomsList = new ArrayList<>();
        this.reportClickListener = reportClickListener;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public SymptomReportAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemSymptomBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_symptom, parent, false);
        return new SymptomReportAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(SymptomReportAdapter.ViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return symptomsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ItemSymptomBinding binding;

        ViewHolder(ItemSymptomBinding bind) {
            super(bind.getRoot());
            this.binding = bind;
        }

        void bindData(final int position) {
            binding.txtName.setText(symptomsList.get(position).getName());
            binding.rating.setRating(symptomsList.get(position).getRating());
            binding.rating.setEnabled(false);
        }
    }

    public List<SymptomsModel> getList() {
        return this.symptomsList;
    }

    public void addAll(List<SymptomsModel> list) {
        this.symptomsList.addAll(list);
        notifyDataSetChanged();
    }

    public void clearAll() {
        this.symptomsList.clear();
        notifyDataSetChanged();
    }

    public interface ReportClickListener {
        public void onReportClick(SymptomsModel symptom, float rating);
    }
}