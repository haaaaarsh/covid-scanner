package com.example.covidscanner.data.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.covidscanner.data.model.Symptoms;

import java.util.List;

@Dao
public interface SymptomsDao {
    @Delete
    void deleteEntry(Symptoms symptoms);

    @Query("SELECT * FROM symptoms")
    List<Symptoms> getSymptoms();

    @Insert
    void insertSymptoms(Symptoms... symptoms);

    @Update
    void updateSymptom(Symptoms symptoms);

    @Query("SELECT * FROM symptoms WHERE userId LIKE :userId")
    List<Symptoms> findByUserId(String userId);

}
