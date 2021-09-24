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
    public float nausea;

    @ColumnInfo(defaultValue = "0")
    public float headache;

    @ColumnInfo(defaultValue = "0")
    public float diarrhea;

    @ColumnInfo(defaultValue = "0")
    public float soarThroat;

    @ColumnInfo(defaultValue = "0")
    public float fever;

    @ColumnInfo(defaultValue = "0")
    public float muscleAche;

    @ColumnInfo(defaultValue = "0")
    public float smellLoss;

    @ColumnInfo(defaultValue = "0")
    public float cough;

    @ColumnInfo(defaultValue = "0")
    public float shortnessBreath;

    @ColumnInfo(defaultValue = "0")
    public float tiredness;

    public Symptoms() {
    }

    public Symptoms(int userId, int creationDate, float nausea, float headache, float diarrhea, float soarThroat, float fever, float muscleAche, float smellLoss, float cough, float shortnessBreath, float tiredness) {
        this.userId = userId;
        this.creationDate = creationDate;
        this.nausea = nausea;
        this.headache = headache;
        this.diarrhea = diarrhea;
        this.soarThroat = soarThroat;
        this.fever = fever;
        this.muscleAche = muscleAche;
        this.smellLoss = smellLoss;
        this.cough = cough;
        this.shortnessBreath = shortnessBreath;
        this.tiredness = tiredness;
    }
}
