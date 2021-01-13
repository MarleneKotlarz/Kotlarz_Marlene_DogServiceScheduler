package com.kotlarz_marlene_dogservicescheduler.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.kotlarz_marlene_dogservicescheduler.Entity.Service;

import java.util.List;

@Dao
public interface ServiceDao {

    @Insert
    void insert(Service service);

    @Update
    void update(Service service);

    @Delete
    void delete(Service service);

    @Query("DELETE FROM ServiceWalking")
    void deleteAllServices();

    @Query("SELECT * FROM ServiceWalking")
    LiveData<List<Service>> getAllServices();


}
