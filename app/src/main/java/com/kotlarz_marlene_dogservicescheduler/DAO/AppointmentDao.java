package com.kotlarz_marlene_dogservicescheduler.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.kotlarz_marlene_dogservicescheduler.Entity.Appointment;

import java.util.List;

@Dao
public interface AppointmentDao {

    @Insert
    void insert(Appointment appointment);

    @Update
    void update(Appointment appointment);

    @Delete
    void delete(Appointment appointment);

    @Query("DELETE FROM appointment_table")
    void deleteAllAppointments();

    @Query("SELECT * FROM appointment_table")
    LiveData<List<Appointment>> getAllAppointments();

    @Query("SELECT * FROM appointment_table WHERE customer_id_fk = :customerIdFk")
    LiveData<List<Appointment>> getAppointmentByCustomerId(int customerIdFk);

//    @Query("SELECT customer_name FROM customer_table INNER JOIN appointment_table on :customerId = customer_id_fk")
//    @Query("SELECT customer_name FROM customer_table WHERE customer_id = 1")
//    String getAppointmentCustomerName();



}
