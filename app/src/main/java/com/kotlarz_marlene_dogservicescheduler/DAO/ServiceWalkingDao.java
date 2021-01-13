package com.kotlarz_marlene_dogservicescheduler.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.kotlarz_marlene_dogservicescheduler.Entity.ServiceWalking;

import java.util.List;

@Dao
public interface ServiceWalkingDao {

    @Insert
    void insert(ServiceWalking serviceWalking);

    @Update
    void update(ServiceWalking serviceWalking);

    @Delete
    void delete(ServiceWalking serviceWalking);

    @Query("SELECT * FROM service_walking_table")
    LiveData<List<ServiceWalking>> getAllServices();

}
