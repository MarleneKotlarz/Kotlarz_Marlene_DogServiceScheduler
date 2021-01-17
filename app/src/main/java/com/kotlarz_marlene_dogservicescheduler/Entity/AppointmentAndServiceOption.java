package com.kotlarz_marlene_dogservicescheduler.Entity;

import androidx.lifecycle.LiveData;
import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class AppointmentAndServiceOption {
    @Embedded
    public Appointment appointment;

    @Relation(
            entity = ServiceOption.class,
            parentColumn = "appointment_id",
            entityColumn = "appointment_id_fk"
    )
//    public List<ServiceOption> serviceOption;
    public ServiceOption serviceOption;

}