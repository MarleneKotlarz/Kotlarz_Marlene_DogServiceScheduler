package com.kotlarz_marlene_dogservicescheduler.Entity;


import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "pet_table",
        foreignKeys = @ForeignKey(
                entity = Customer.class,
                parentColumns = "customer_id",
                childColumns = "customer_id_fk",
                onDelete = ForeignKey.CASCADE))
public class Pet {
    @PrimaryKey(autoGenerate = true)
    private int pet_id;
    private int customer_id_fk;

    private String pet_name, pet_breed, pet_age;
    @Nullable
    String pet_note;

    // Constructor
    public Pet(int customer_id_fk, String pet_age, String pet_name, String pet_breed, String pet_note) {
        this.customer_id_fk = customer_id_fk;
        this.pet_age = pet_age;
        this.pet_name = pet_name;
        this.pet_breed = pet_breed;
        this.pet_note = pet_note;
    }

    // Getters and Setters
    public int getPet_id() {
        return pet_id;
    }

    public void setPet_id(int pet_id) {
        this.pet_id = pet_id;
    }

    public int getCustomer_id_fk() {
        return customer_id_fk;
    }

    public void setCustomer_id_fk(int customer_id_fk) {
        this.customer_id_fk = customer_id_fk;
    }

    public String getPet_age() {
        return pet_age;
    }

    public void setPet_age(String pet_age) {
        this.pet_age = pet_age;
    }

    public String getPet_name() {
        return pet_name;
    }

    public void setPet_name(String pet_name) {
        this.pet_name = pet_name;
    }

    public String getPet_breed() {
        return pet_breed;
    }

    public void setPet_breed(String pet_breed) {
        this.pet_breed = pet_breed;
    }

    public String getPet_note() {
        return pet_note;
    }

    public void setPet_note(String pet_note) {
        this.pet_note = pet_note;
    }
}
