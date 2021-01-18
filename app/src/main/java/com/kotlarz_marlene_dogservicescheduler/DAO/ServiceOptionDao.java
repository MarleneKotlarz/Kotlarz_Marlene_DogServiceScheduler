package com.kotlarz_marlene_dogservicescheduler.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.kotlarz_marlene_dogservicescheduler.Entity.ServiceOption;

import java.util.List;

@Dao
public interface ServiceOptionDao {

    @Insert
    void insert(ServiceOption serviceOption);

    @Update
    void update(ServiceOption serviceOption);

    @Delete
    void delete(ServiceOption serviceOption);

    @Query("SELECT * FROM service_option_table")
    LiveData<List<ServiceOption>> getAllServiceOptions();

}
