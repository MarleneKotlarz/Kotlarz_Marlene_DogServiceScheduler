package com.kotlarz_marlene_dogservicescheduler.Entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;

@Entity(tableName = "service_option_table")
//        foreignKeys = {
//                @ForeignKey(
//                        entity = Appointment.class,
//                        parentColumns = "appointment_id",
//                        childColumns = "appointment_id_fk",
//                        onDelete = ForeignKey.CASCADE)})

public class ServiceOption extends Service {
    private String option;

    // Constructor ServiceWalking
    public ServiceOption(String duration, String location, String type, String option) {
        super(duration, location, type);
        this.option = option;
    }

    @Ignore
    // Constructor ServiceOption
    public ServiceOption(String duration, String type, String option) {
        super(duration, type);
        setLocation("Home");
        this.option = option;
    }


    // Getter and Setter
    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }
}