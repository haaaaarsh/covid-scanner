package com.example.covidscanner.ui.symptom;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covidscanner.R;
import com.example.covidscanner.databinding.ItemSymptomBinding;

import java.util.List;

public class SymptomsAdapter extends RecyclerView.Adapter<SymptomsAdapter.ViewHolder> {
    private List<SymptomsModel> symptomsList;
    private Activity context;
    private LayoutInflater inflater;
    private NotificationClickListener notificationClickListener;

    public SymptomsAdapter(Activity context, List<SymptomsModel> symptomsList, NotificationClickListener notificationClickListener) {
        this.context = context;
        this.symptomsList = symptomsList;
        this.notificationClickListener = notificationClickListener;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public SymptomsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemSymptomBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_symptom, parent, false);
        return new SymptomsAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(SymptomsAdapter.ViewHolder holder, int position) {
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
//            binding.rlRoot.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    notificationClickListener.onNotificationClick(position, notificationsList.get(position).getId(), notificationsList.get(position).getMessage());
//                }
//            });
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

    public interface NotificationClickListener {
        public void onNotificationClick(int position, String notificationId, String message);
    }
}