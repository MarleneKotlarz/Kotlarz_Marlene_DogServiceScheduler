package com.kotlarz_marlene_dogservicescheduler.Entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "appointment_table",
        foreignKeys = {
                @ForeignKey(
                        entity = Employee.class,
                        parentColumns = "employee_id",
                        childColumns = "employee_id_fk",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = Customer.class,
                        parentColumns = "customer_id",
                        childColumns = "customer_id_fk",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = Pet.class,
                        parentColumns = "pet_id",
                        childColumns = "pet_id_fk",
                        onDelete = ForeignKey.CASCADE),
//                @ForeignKey(
//                        entity = Service.class,
//                        parentColumns = "service_id",
//                        childColumns = "service_id_fk",
//                        onDelete = ForeignKey.CASCADE),
        })
public class Appointment {
    @PrimaryKey(autoGenerate = true)
    private int appointment_id;
    private int employee_id_fk;
    private int customer_id_fk;
    private int pet_id_fk;
//    private int service_id_fk;

    private String date, time;



    // Constructor
    public Appointment(int employee_id_fk, int customer_id_fk, int pet_id_fk, String date, String time) {
        this.employee_id_fk = employee_id_fk;
        this.customer_id_fk = customer_id_fk;
        this.pet_id_fk = pet_id_fk;
        this.date = date;
        this.time = time;
    }

    // Getters and Setters
    public int getAppointment_id() {
        return appointment_id;
    }

    public void setAppointment_id(int appointment_id) {
        this.appointment_id = appointment_id;
    }

    public int getEmployee_id_fk() {
        return employee_id_fk;
    }

    public void setEmployee_id_fk(int employee_id_fk) {
        this.employee_id_fk = employee_id_fk;
    }

    public int getCustomer_id_fk() {
        return customer_id_fk;
    }

    public void setCustomer_id_fk(int customer_id_fk) {
        this.customer_id_fk = customer_id_fk;
    }

    public int getPet_id_fk() {
        return pet_id_fk;
    }

    public void setPet_id_fk(int pet_id_fk) {
        this.pet_id_fk = pet_id_fk;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}






