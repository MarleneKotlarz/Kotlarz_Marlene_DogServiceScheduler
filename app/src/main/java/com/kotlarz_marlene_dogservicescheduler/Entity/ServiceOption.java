package com.kotlarz_marlene_dogservicescheduler.Entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;

//---------- CODE INCLUDING INHERITANCE, POLYMORPHISM, AND ENCAPSULATION ----------//

@Entity(tableName = "service_option_table",
        foreignKeys = {
                @ForeignKey(
                        entity = Appointment.class,
                        parentColumns = "appointment_id",
                        childColumns = "appointment_id_fk",
                        onDelete = ForeignKey.CASCADE)})

public class ServiceOption extends Service {
    private int appointment_id_fk;
    private String option;

    // Constructor ServiceWalking
    public ServiceOption(String duration, String location, String type, int appointment_id_fk, String option) {
        super(duration, location, type);
        this.appointment_id_fk = appointment_id_fk;
        this.option = option;
    }

    // Getter and Setter
    public int getAppointment_id_fk() {
        return appointment_id_fk;
    }

    public void setAppointment_id_fk(int appointment_id_fk) {
        this.appointment_id_fk = appointment_id_fk;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }


}