package com.varun.matic_networks.RoomDB;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UserDao {

    //@Query("SELECT * FROM UserData")
    //List<UserData> getAll();

    @Query("SELECT Count(*) FROM UserData WHERE username =:username AND password =:password")
    int checkUserExists(String username, String password);

    @Insert
    void insertAll(UserData... userData);
}