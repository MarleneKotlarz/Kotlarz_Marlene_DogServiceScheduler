package com.kotlarz_marlene_dogservicescheduler.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.kotlarz_marlene_dogservicescheduler.Entity.Pet;

import java.util.List;

@Dao
public interface PetDao {

    @Insert
    void insert(Pet pet);

    @Update
    void update(Pet pet);

    @Delete
    void delete(Pet pet);

    @Query("SELECT * FROM pet_table")
    LiveData<List<Pet>> getAllPets();

    @Query("SELECT * FROM pet_table WHERE customer_id_fk = :customerIdFk")
    LiveData<List<Pet>> getPetByCustomerId(int customerIdFk);

}
