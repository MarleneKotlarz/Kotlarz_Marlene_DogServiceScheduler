package com.kotlarz_marlene_dogservicescheduler.Entity;

import androidx.room.Embedded;
import androidx.room.Relation;

public class AppointmentAndServiceOption {
    @Embedded
    public Appointment appointment;

    @Relation(
            entity = ServiceOption.class,
            parentColumn = "appointment_id",
            entityColumn = "appointment_id_fk"
    )

    public ServiceOption serviceOption;

}
