package com.kotlarz_marlene_dogservicescheduler.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.kotlarz_marlene_dogservicescheduler.Entity.ServicePlaying;
import com.kotlarz_marlene_dogservicescheduler.Entity.ServiceWalking;

import java.util.List;

@Dao
public interface ServicePlayingDao {

    @Insert
    void insert(ServicePlaying servicePlaying);

    @Update
    void update(ServicePlaying servicePlaying);

    @Delete
    void delete(ServicePlaying servicePlaying);

    @Query("SELECT * FROM service_playing_table")
    LiveData<List<ServicePlaying>> getAllPlayingServices();

}
