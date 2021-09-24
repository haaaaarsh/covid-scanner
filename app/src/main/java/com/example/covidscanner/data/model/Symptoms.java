package com.example.covidscanner.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "symptoms")
public class Symptoms {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(defaultValue = "0")
    public int userId;

    @ColumnInfo(defaultValue = "0")
    public int creationDate;

    @ColumnInfo(defaultValue = "0")
    public int nausea;

    @ColumnInfo(defaultValue = "0")
    public int headache;

    @ColumnInfo(defaultValue = "0")
    public int diarrhea;

    @ColumnInfo(defaultValue = "0")
    public int soarThroat;

    @ColumnInfo(defaultValue = "0")
    public int fever;

    @ColumnInfo(defaultValue = "0")
    public int muscleAche;

    @ColumnInfo(defaultValue = "0")
    public int smellLoss;

    @ColumnInfo(defaultValue = "0")
    public int cough;

    @ColumnInfo(defaultValue = "0")
    public int shortnessBreath;

    @ColumnInfo(defaultValue = "0")
    public int tiredness;
}
