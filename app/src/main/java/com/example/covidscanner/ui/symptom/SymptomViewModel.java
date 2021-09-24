package com.example.covidscanner.ui.symptom;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.covidscanner.R;
import com.example.covidscanner.data.db.AppDatabase;
import com.example.covidscanner.data.db.dao.SymptomsDao;
import com.example.covidscanner.data.model.Symptoms;
import com.example.covidscanner.ui.base.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

public class SymptomViewModel extends BaseViewModel<SymptomNavigator> {

    AppDatabase db;

    private final MutableLiveData<List<SymptomsModel>> symptomsLiveData;

    public SymptomViewModel() {
        db = AppDatabase.getInstance();
        symptomsLiveData = new MutableLiveData<>();
    }

    public void setSymptomsList() {
        class SymptomsListTask extends AsyncTask<Void, Void, List<SymptomsModel>> {
            @Override
            protected List<SymptomsModel> doInBackground(Void... voids) {
                String[] symptomNames = getNavigator().getActivityContext().getResources().getStringArray(R.array.symptomList);
                List<SymptomsModel> symptomsList = new ArrayList<>();

                SymptomsDao symptomsDao = db.symptomsDao();
                Symptoms symptoms = symptomsDao.getSymptoms() != null && !symptomsDao.getSymptoms().isEmpty() ? symptomsDao.getSymptoms().get(symptomsDao.getSymptoms().size() - 1) : null;

                for (int i = 0; i < symptomNames.length; i++) {
                    switch (symptomNames[i]) {
                        case "Nausea":
                            symptomsList.add(new SymptomsModel(symptomNames[i], symptoms!= null ? symptoms.nausea : 0));
                            break;
                        case "Headache":
                            symptomsList.add(new SymptomsModel(symptomNames[i], symptoms!= null ?  symptoms.headache : 0));
                            break;
                        case "Diarrhea":
                            symptomsList.add(new SymptomsModel(symptomNames[i], symptoms!= null ?  symptoms.diarrhea : 0));
                            break;
                        case "Soar Throat":
                            symptomsList.add(new SymptomsModel(symptomNames[i], symptoms!= null ?  symptoms.soarThroat : 0));
                            break;
                        case "Fever":
                            symptomsList.add(new SymptomsModel(symptomNames[i], symptoms!= null ?  symptoms.fever : 0));
                            break;
                        case "Muscle Ache":
                            symptomsList.add(new SymptomsModel(symptomNames[i], symptoms!= null ?  symptoms.muscleAche : 0));
                            break;
                        case "Loss of Smell or Taste":
                            symptomsList.add(new SymptomsModel(symptomNames[i], symptoms!= null ?  symptoms.smellLoss : 0));
                            break;
                        case "Cough":
                            symptomsList.add(new SymptomsModel(symptomNames[i], symptoms!= null ?  symptoms.cough : 0));
                            break;
                        case "Shortness of Breath":
                            symptomsList.add(new SymptomsModel(symptomNames[i], symptoms!= null ?  symptoms.shortnessBreath : 0));
                            break;
                        case "Feeling tired":
                            symptomsList.add(new SymptomsModel(symptomNames[i], symptoms!= null ?  symptoms.tiredness : 0));
                            break;
                    }
                }
                return symptomsList;
            }

            @Override
            protected void onPostExecute(List<SymptomsModel> symptomsList) {
                super.onPostExecute(symptomsList);
                symptomsLiveData.setValue(symptomsList);
            }
        }

        SymptomsListTask symptomsListTask = new SymptomsListTask();
        symptomsListTask.execute();
    }

    public LiveData<List<SymptomsModel>> getSymptomsLiveData() {
        return symptomsLiveData;
    }
}
