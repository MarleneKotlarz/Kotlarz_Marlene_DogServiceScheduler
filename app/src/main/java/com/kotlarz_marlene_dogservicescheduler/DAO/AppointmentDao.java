package com.kotlarz_marlene_dogservicescheduler.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.kotlarz_marlene_dogservicescheduler.Entity.Appointment;
import com.kotlarz_marlene_dogservicescheduler.Entity.AppointmentAndServiceOption;

import java.util.List;



@Dao
public interface AppointmentDao {


    @Insert
    void insert(Appointment appointment);

    @Update
    void update(Appointment appointment);

    @Delete
    void delete(Appointment appointment);

    @Query("SELECT * FROM appointment_table ORDER BY date ASC")
    LiveData<List<Appointment>> getAllAppointments();

    @Query("SELECT appointment_id FROM appointment_table ORDER BY appointment_id DESC LIMIT 1")
    Integer getAppointmentIdForService();

    @Transaction
    @Query("SELECT * FROM appointment_table ORDER BY date ASC")
    LiveData<List<AppointmentAndServiceOption>> getAppointmentAndServiceOptions();

    @Transaction
    @Query("SELECT * FROM appointment_table WHERE appointment_id = :appointmentId")
    LiveData<List<AppointmentAndServiceOption>> getAppointmentAndServiceByApptId(int appointmentId);

}
