package com.kotlarz_marlene_dogservicescheduler.Entity;


import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "customer_table",
        foreignKeys = @ForeignKey(
                entity = Employee.class,
                parentColumns = "employee_id",
                childColumns = "employee_id_fk",
                onDelete = ForeignKey.CASCADE))
public class Customer {
    @PrimaryKey(autoGenerate = true)
    private int customer_id;
    private int employee_id_fk;
    private String customer_name, customer_address, customer_phone;


    // Constructor
    public Customer(int employee_id_fk, String customer_name, String customer_address, String customer_phone) {
        this.employee_id_fk = employee_id_fk;
        this.customer_name = customer_name;
        this.customer_address = customer_address;
        this.customer_phone = customer_phone;
    }

    // Getters and Setters
    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getEmployee_id_fk() {
        return employee_id_fk;
    }

    public void setEmployee_id_fk(int employee_id_fk) {
        this.employee_id_fk = employee_id_fk;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomer_address() {
        return customer_address;
    }

    public void setCustomer_address(String customer_address) {
        this.customer_address = customer_address;
    }

    public String getCustomer_phone() {
        return customer_phone;
    }

    public void setCustomer_phone(String customer_phone) {
        this.customer_phone = customer_phone;
    }
}
