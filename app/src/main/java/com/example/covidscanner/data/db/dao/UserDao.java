package com.example.covidscanner.data.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.covidscanner.data.model.User;

import java.util.List;

@Dao
public interface UserDao {
    @Delete
    void deleteUser(User user);

    @Query("SELECT * FROM user")
    List<User> getUser();

    @Insert
    void insertUser(User... user);

    @Update
    void updateUser(User user);
}
