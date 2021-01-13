package com.kotlarz_marlene_dogservicescheduler.Entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "employee_table")
public class Employee {
    @PrimaryKey(autoGenerate = true)
    private int employee_id;
    private String employee_name, employee_phone, employee_password;

    // Constructor
    public Employee(String employee_name, String employee_phone, String employee_password) {
        this.employee_name = employee_name;
        this.employee_phone = employee_phone;
        this.employee_password = employee_password;
    }

    // Getters and Setters
    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public String getEmployee_name() {
        return employee_name;
    }

    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
    }

    public String getEmployee_phone() {
        return employee_phone;
    }

    public void setEmployee_phone(String employee_phone) {
        this.employee_phone = employee_phone;
    }

    public String getEmployee_password() {
        return employee_password;
    }

    public void setEmployee_password(String employee_password) {
        this.employee_password = employee_password;
    }
}
