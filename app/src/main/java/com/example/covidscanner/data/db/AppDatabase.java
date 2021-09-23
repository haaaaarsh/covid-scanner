package com.example.covidscanner.data.db;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.covidscanner.CovidScannerApp;
import com.example.covidscanner.data.db.dao.UserDao;
import com.example.covidscanner.data.model.User;

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase mInstance;

    public abstract UserDao userDao();

    public static AppDatabase getInstance() {
        if (mInstance == null) {
            mInstance = Room.databaseBuilder(CovidScannerApp.getInstance(), AppDatabase.class, "covidscanner.db").build();
        }
        return mInstance;
    }
}
